package com.hfw.admin.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.read.metadata.holder.ReadSheetHolder;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import com.alibaba.excel.support.cglib.beans.BeanMap;
import com.alibaba.excel.util.BeanMapUtils;
import com.alibaba.excel.util.ClassUtils;
import com.alibaba.excel.util.ConverterUtils;
import com.hfw.common.entity.BaseEntity;

import java.lang.reflect.Field;
import java.util.*;

/**
 * easyexcel 增强监听器
 * @author farkle
 * @date 2023-02-27
 */
public class ExtListener<T> extends AnalysisEventListener<T> {
    /**
     * 数据处理
     */
    private EasyExcelDataHander<T> excelDataHander;
    /**
     * 导入方式
     */
    private ImportEnum importType;
    /**
     * 数据处理方式
     */
    private DataHandleEnum dataHandleType;
    /**
     * 批量操作最大数量
     */
    private int bacthCnt = 1000;
    /**
     * 成功数量
     */
    private int successCnt = 0;
    /**
     * 失败数量
     */
    private int failCnt = 0;
    /**
     * 解析数据
     */
    private List<T> list = new ArrayList();
    /**
     * filter_fail导入方式下记录的错误数据
     */
    private List<T> errorList = new ArrayList<>();
    /**
     * 记录的list数据中校验错误的index
     * list的index为key
     * errorList的index为value
     */
    private Map<Integer,Integer> errorIndexs = new HashMap<>();
    /**
     * 错误信息
     * 索引_字段名 为key
     */
    private Map<String, ErrorData> errorMap = new HashMap();
    /**
     * auto导入方式下保留的filter_fail导入方式的错误信息
     */
    private Map<String, ErrorData> filterFailErrorMap = new HashMap<>();

    /*public List<T> getList() {
        return this.list;
    }*/

    public Map<String, ErrorData> getErrorMap() {
        return errorMap;
    }
    public boolean hasError(){
        return this.errorMap.size()>0;
    }

    public List<T> getErrorList(){
        return this.importType==ImportEnum.filter_fail ?this.errorList :this.list;
    }

    public void setBacthCnt(int bacthCnt){
        this.bacthCnt = bacthCnt;
    }

    public DataHandleEnum getDataHandleType() {
        return dataHandleType;
    }

    public ImportEnum getImportType() {
        return importType;
    }

    public int getSuccessCnt() {
        return successCnt;
    }

    public Set<Integer> getErrorIndexs() {
        return errorIndexs.keySet();
    }

    public int getFailCnt() {
        return failCnt;
    }

    public ExtListener(EasyExcelDataHander<T> excelDataHander, ImportEnum importType, DataHandleEnum dataHandleType){
        this.excelDataHander = excelDataHander;
        this.importType = importType;
        this.dataHandleType = dataHandleType;
    }

    /**
     * easyexcel 数据处理
     * @param object
     * @param analysisContext
     */
    @Override
    public void invoke(T object, AnalysisContext analysisContext) {
        this.list.add(object);
        if(list.size()>=bacthCnt){
            if(ImportEnum.auto == this.importType){
                this.importType = ImportEnum.filter_fail;
                this.errorMap = this.filterFailErrorMap;
            }
            if(ImportEnum.filter_fail == this.importType ){
                successCnt += excelDataHander.handleData(this.list, this);
                list = new ArrayList<>(bacthCnt);
                failCnt += this.errorIndexs.size();
                this.errorIndexs.clear();
            }
        }
    }

    /**
     * easyexcel 额外信息(批注、超链接、合并单元格信息)处理
     * @param extra
     * @param context
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

    /**
     * 解析完毕
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        successCnt += excelDataHander.handleData(this.list, this);
        failCnt += this.errorIndexs.size();
    }

    /**
     * 异常处理
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        if(exception instanceof ExcelDataConvertException){
            //ExcelDataConvertException e  = (ExcelDataConvertException)exception;
            Map<Integer, ReadCellData<?>> cellDataMap = (Map<Integer, ReadCellData<?>>) context.readRowHolder().getCurrentRowAnalysisResult();
            Object obj = this.buildUserModel(cellDataMap, context.readSheetHolder(), context);
            this.addErrorData(this.list.size(), (T) obj);
            this.list.add((T) obj);
        }else{
            throw exception;
        }
    }

    /**
     * cglib 构建实体对象
     * @param cellDataMap
     * @param readSheetHolder
     * @param context
     * @return
     */
    private Object buildUserModel(Map<Integer, ReadCellData<?>> cellDataMap, ReadSheetHolder readSheetHolder, AnalysisContext context) {
        ExcelReadHeadProperty excelReadHeadProperty = readSheetHolder.excelReadHeadProperty();

        Object resultModel;
        try {
            resultModel = excelReadHeadProperty.getHeadClazz().newInstance();
        } catch (Exception var15) {
            throw new ExcelDataConvertException(context.readRowHolder().getRowIndex(), 0, new ReadCellData(CellDataTypeEnum.EMPTY), (ExcelContentProperty)null, "Can not instance class: " + excelReadHeadProperty.getHeadClazz().getName(), var15);
        }

        Map<Integer, Head> headMap = excelReadHeadProperty.getHeadMap();
        BeanMap dataMap = BeanMapUtils.create(resultModel);
        Iterator var8 = headMap.entrySet().iterator();

        StringJoiner joiner = new StringJoiner("；");
        while(var8.hasNext()) {
            Map.Entry<Integer, Head> entry = (Map.Entry)var8.next();
            Integer index = (Integer)entry.getKey();
            Head head = (Head)entry.getValue();
            String fieldName = head.getFieldName();
            if (cellDataMap.containsKey(index)) {
                ReadCellData<?> cellData = (ReadCellData)cellDataMap.get(index);
                try{
                    Object value = ConverterUtils.convertToJavaObject(cellData, head.getField(), ClassUtils.declaredExcelContentProperty(dataMap, readSheetHolder.excelReadHeadProperty().getHeadClazz(), fieldName), readSheetHolder.converterMap(), context, context.readRowHolder().getRowIndex(), index);
                    if (value != null) {
                        dataMap.put(fieldName, value);
                    }
                }catch (Exception e){
                    String header = head.getHeadNameList().size()>0 ?head.getHeadNameList().get(0):"";
                    String text = "";
                    if(cellData.getStringValue()!=null){
                        text += cellData.getStringValue();
                    }else if(cellData.getNumberValue()!=null){
                        text += cellData.getNumberValue();
                    }else if(cellData.getBooleanValue()!=null){
                        text += cellData.getBooleanValue();
                    }
                    //String error = String.format("第%d行[%s:%s]错误",cellData.getRowIndex()+1, header, text);
                    String error = String.format("%s[%s]错误", header, text);
                    joiner.add(error);
                    this.validFail(this.list.size(), fieldName, error, null, cellData);
                }
            }
        }
        if(resultModel instanceof BaseEntity){
            ((BaseEntity) resultModel).setError(joiner.toString());
        }
        return resultModel;
    }

    /**
     * 校验错误处理
     * @param index
     * @param fieldName
     * @param error
     * @param t
     * @param cellData
     */
    private void validFail(int index, String fieldName, String error, T t, ReadCellData<?> cellData){
        if(t!=null && t instanceof BaseEntity){
            BaseEntity data = (BaseEntity)t;
            if(data.getError()==null){
                data.setError(error);
            }else{
                data.setError( data.getError()+"；"+error);
            }
        }
        ErrorData errorData = new ErrorData();
        errorData.setIndex(index);
        errorData.setFieldName(fieldName);
        errorData.setError(error);
        if(cellData != null){
            errorData.setCellDataType(cellData.getType());
            errorData.setBooleanValue(cellData.getBooleanValue());
            errorData.setNumberValue(cellData.getNumberValue());
            errorData.setStringValue(cellData.getStringValue());
        }
        int errorIndex = this.addErrorData(index, t);
        if(ImportEnum.all == this.importType){
            this.errorMap.put(index+"_"+errorData.getFieldName(), errorData);
        }else if(ImportEnum.filter_fail == this.importType){
            this.errorMap.put(errorIndex+"_"+errorData.getFieldName(), errorData);
        }else if(ImportEnum.auto == this.importType){
            this.errorMap.put(index+"_"+errorData.getFieldName(), errorData);
            this.filterFailErrorMap.put(errorIndex+"_"+errorData.getFieldName(), errorData);
        }
    }
    private int addErrorData(int index, T t){
        int errorIndex = this.errorList.size();
        if(this.errorIndexs.containsKey(index)){
            errorIndex = this.errorIndexs.get(index);
        }else if(/*ImportEnum.all!=this.importType &&*/ t!=null){
            this.errorIndexs.put(index, errorIndex);
            this.errorList.add(t);
        }
        return errorIndex;
    }

    /**
     * 校验错误处理
     * @param index
     * @param fieldName
     * @param error
     * @param t
     */
    public void validFail(int index, String fieldName, String error, T t){
        this.validFail(index, fieldName, error, t , null);
    }
}
