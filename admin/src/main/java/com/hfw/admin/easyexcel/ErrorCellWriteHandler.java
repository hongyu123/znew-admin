package com.hfw.admin.easyexcel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.List;
import java.util.Map;

/**
 * 单元格写错误增强处理
 * @author farkle
 * @date 2023-03-16
 */
public class ErrorCellWriteHandler implements CellWriteHandler {
    //索引_字段名,错误信息
    private Map<String, ErrorData> errorMap;

    public ErrorCellWriteHandler(Map<String, ErrorData> errorMap){
        this.errorMap = errorMap;
    }

    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, WriteCellData<?> cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        if(isHead!=null && !isHead){
            String index = relativeRowIndex+"_"+ head.getFieldName();
            ErrorData errorData = errorMap.get(index);
            if(errorData!=null){
                WriteCellStyle cellStyle = cellData.getOrCreateStyle();
                if(errorData.getStringValue()==null && errorData.getNumberValue()==null){
                    /*设置背景色*/
                    cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
                    cellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
                }else {
                    /*设置字体*/
                    WriteFont writeFont = new WriteFont();
                    writeFont.setColor(IndexedColors.RED.getIndex());
                    cellStyle.setWriteFont(writeFont);
                }
                if(errorData.getCellDataType()!=null){
                    cellData.setType(errorData.getCellDataType());
                    cellData.setStringValue(errorData.getStringValue());
                    cellData.setNumberValue(errorData.getNumberValue());
                    cellData.setBooleanValue(errorData.getBooleanValue());
                }
            }
        }
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    }

}
