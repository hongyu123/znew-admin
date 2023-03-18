package com.hfw.admin.easyexcel;

import com.hfw.common.support.jackson.ApiResult;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyExcel导入数据处理接口
 * @author farkle
 * @date 2023-03-09
 */
public interface EasyExcelDataHander<T> {

    /**
     * 数据校验
     * @param list
     * @param listener
     * @return
     */
    default ApiResult<List<T>> validData(List<T> list, ExtListener<T> listener){
        //部分导入,过滤失败数据
        if(ImportEnum.filter_fail == listener.getImportType()){
            List<T> successList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                int errorSize = listener.getErrorList().size();
                this.validData(i, list.get(i), listener);
                if(errorSize == listener.getErrorList().size() && !listener.getErrorIndexs().contains(i)){
                    successList.add(list.get(i));
                }
            }
            int code = successList.size()>0 ?ApiResult.SUCCESS_CODE: ApiResult.ERROR_CODE;
            return ApiResult.data(code, successList);
        }
        //整体导入
        for (int i = 0; i < list.size(); i++) {
            this.validData(i, list.get(i), listener);
        }
        int code = listener.hasError() ?ApiResult.ERROR_CODE: ApiResult.SUCCESS_CODE;
        return ApiResult.data(code, list);
    }

    /**
     * 单条数据校验
     * @param index
     * @param demo
     * @param listener
     */
    void validData(int index, T demo, ExtListener<T> listener);

    /**
     * 数据处理
     * @param list
     * @param listener
     */
    default int handleData(List<T> list, ExtListener<T> listener){
        ApiResult<List<T>> res = this.validData(list, listener);
        if(res.isSuccess()){
            return this.handleData(res.getData(), listener.getDataHandleType(), listener);
        }
        return 0;
    }

    /**
     * 根据数据处理类型对数据进行处理
     * @param list
     * @param dataHandleType
     * @param listener
     */
    default int handleData(List<T> list, DataHandleEnum dataHandleType, ExtListener<T> listener){
        if(DataHandleEnum.cover == dataHandleType){
            return this.cover(list,false);
        }else if(DataHandleEnum.update == dataHandleType){
            return this.update(list);
        }else{
            return this.insert(list);
        }
    }

    /**
     * 新增导入
     * @param list
     */
    int insert(List<T> list);

    /**
     * 覆盖导入
     * @param list
     * @param update 更新导入
     */
    int cover(List<T> list, boolean update);

    /**
     * 更新导入
     * @param list
     */
    default int update(List<T> list){
        return this.cover(list, true);
    }

}
