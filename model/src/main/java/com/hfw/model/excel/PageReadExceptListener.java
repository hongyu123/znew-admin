package com.hfw.model.excel;

import org.apache.fesod.sheet.annotation.ExcelProperty;
import org.apache.fesod.sheet.context.AnalysisContext;
import org.apache.fesod.sheet.exception.ExcelDataConvertException;
import org.apache.fesod.sheet.metadata.data.CellData;
import org.apache.fesod.sheet.read.listener.PageReadListener;
import org.apache.poi.ss.util.CellReference;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PageReadExceptListener<T> extends PageReadListener<T> {
    private int dataHandleIndex; //从1开始
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
