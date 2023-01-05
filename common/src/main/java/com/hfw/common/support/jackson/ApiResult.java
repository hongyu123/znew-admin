package com.hfw.common.support.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.hfw.common.enums.IEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * api接口返回实体
 * @author zyh
 * @date 2022-04-15
 */
@Data
public class ApiResult<T> {
    private int code;
    private String message;
    private T data;

    /** 成功消息  */
    public static final String SUCCESS_MESSAGE = "操作成功";
    /** 错误消息 */
    public static final String ERROR_MESSAGE = "操作失败";
    public static final String includeFilter = "includeFilter";

    public ApiResult(){
    }
    public ApiResult(int code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ApiResult success(){
        return new ApiResult(1,SUCCESS_MESSAGE,"");
    }
    public static ApiResult success(String message){
        return new ApiResult(1,message,"");
    }
    public static ApiResult error(String message){
        return new ApiResult(0,message,"");
    }
    public static ApiResult message(int code, String message){
        return new ApiResult(code,message,"");
    }
    public static ApiResult message(IEnum iEnum){
        return new ApiResult(iEnum.getCode(),iEnum.getDesc(),"");
    }

    public static ApiResult data(Object data){
        if(data == null){
            data = new Object();
        }
        return new ApiResult(1,null,data);
    }
    public static ApiResult str(String data){
        if(data == null){
            data = "";
        }
        return new ApiResult(1,null,data);
    }
    public static ApiResult list(List list){
        if(list == null){
            list = new ArrayList<>();
        }
        return new ApiResult(1,null,list);
    }

    private static String json(ApiResult apiResult,String filterName, String... fields) throws JsonProcessingException {
        ObjectMapper objectMapper = NullHandleObjectMapper.build();
        //2.1创建过滤器
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        //2.1.1Person类的属性过滤器（只序列化car,house,name字段）
        filterProvider.addFilter(filterName, SimpleBeanPropertyFilter.filterOutAllExcept(fields));
        objectMapper.setFilterProvider(filterProvider);
        return objectMapper.writeValueAsString(apiResult);
    }
    public static String jsonList(List list, String filterName, String... fields) throws JsonProcessingException {
        if(list == null){
            list = new ArrayList<>();
        }
        ApiResult apiResult = new ApiResult(1, null, list);
        return json(apiResult,filterName,fields);
    }
    public static String jsonData(Object data,String filterName, String... fields) throws JsonProcessingException {
        if(data == null){
            data = new Object();
        }
        ApiResult apiResult = new ApiResult(1, null, data);
        return json(apiResult,filterName,fields);
    }
}
