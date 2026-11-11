package com.hfw.model.excel;

import org.apache.fesod.sheet.annotation.ExcelProperty;
import org.apache.fesod.sheet.context.AnalysisContext;
import org.apache.fesod.sheet.enums.CellExtraTypeEnum;
import org.apache.fesod.sheet.exception.ExcelDataConvertException;
import org.apache.fesod.sheet.metadata.CellExtra;
import org.apache.fesod.sheet.metadata.Head;
import org.apache.fesod.sheet.metadata.data.CellData;
import org.apache.fesod.sheet.read.listener.ReadListener;
import org.apache.fesod.sheet.read.metadata.property.ExcelReadHeadProperty;
import org.apache.poi.ss.util.CellReference;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReadAllListener<T> implements ReadListener<T> {
    private int dataHandleIndex = 0; //从1开始
    private List<T> list = new ArrayList<>();
    private List<String> errorList = new ArrayList<>();

    public ReadAllListener(){}
    public ReadAllListener(int dataHandleIndex){
        this.dataHandleIndex = dataHandleIndex;
    }

    public List<T> getList() {
        return this.list;
    }
    public List<String> getErrorList() {
        return errorList;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        //rowIndex从0开始
        Integer rowIndex = analysisContext.readRowHolder().getRowIndex();
        if(rowIndex<dataHandleIndex){
            return;
        }
        this.list.add(t);
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        //rowIndex从0开始
        Integer rowIndex = context.readRowHolder().getRowIndex();
        if(rowIndex<dataHandleIndex){
            return;
        }
        if(exception instanceof ExcelDataConvertException e){
            CellData<?> cellData = e.getCellData();
            String column = CellReference.convertNumToColString(cellData.getColumnIndex());
            ExcelProperty anno = e.getExcelContentProperty().getField().getAnnotation(ExcelProperty.class);
            if(anno!=null){
                String annoTitle = String.join(",", anno.value());
                String error = String.format("第%d行%s列[%s:%s]格式错误",cellData.getRowIndex()+1,column, annoTitle, cellData.getStringValue());
                errorList.add(error);
            }else {
                String error = String.format("第%d行%s列[%s]格式错误",cellData.getRowIndex()+1,column, cellData.getStringValue());
                errorList.add(error);
            }
            return;
        }
        throw exception;
    }

    /**
     * 额外信息(批注、超链接、合并单元格信息)处理
     */
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        //analysisContext.readSheetHolder().excelReadHeadProperty()
        ExcelReadHeadProperty headProperty = context.currentReadHolder().excelReadHeadProperty();
        if(CellExtraTypeEnum.MERGE != extra.getType()){
            return;
        }
        //合并行属性处理
        if(extra.getRowIndex()>headProperty.getHeadRowNumber()-1){
            Head head = headProperty.getHeadMap().get(extra.getColumnIndex());
            if(head==null){
                return;
            }
            Field field = head.getField();
            if(field==null){
                return;
            }
            try {
                field.setAccessible(true);
                int index = extra.getFirstRowIndex()-headProperty.getHeadRowNumber();
                Object value = field.get(this.list.get(index));
                if(value!=null){
                    for(int i =index+1; i<=extra.getLastRowIndex()-headProperty.getHeadRowNumber(); i++){
                        field.set(this.list.get(i), value);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

}
