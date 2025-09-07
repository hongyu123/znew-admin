package com.hfw.model.excel;

import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.exception.ExcelDataConvertException;
import cn.idev.excel.metadata.data.CellData;
import cn.idev.excel.read.listener.ReadListener;
import org.apache.poi.ss.util.CellReference;

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

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

}
