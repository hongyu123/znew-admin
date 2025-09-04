package com.hfw.model.excel;

import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.exception.ExcelDataConvertException;
import cn.idev.excel.metadata.data.CellData;
import cn.idev.excel.read.listener.PageReadListener;
import org.apache.poi.ss.util.CellReference;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PageReadExceptListener<T> extends PageReadListener<T> {
    private int dataHandleIndex;
    private List<String> errorList = new ArrayList<>();
    public List<String> getErrorList(){
        return this.errorList;
    }
    public PageReadExceptListener(Consumer<List<T>> consumer) {
        super(consumer, 1000);
    }

    public PageReadExceptListener(Consumer<List<T>> consumer, int batchCount) {
        super(consumer, batchCount);
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

}
