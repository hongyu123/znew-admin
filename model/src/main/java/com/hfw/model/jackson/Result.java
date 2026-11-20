package com.hfw.model.jackson;

import com.hfw.model.enums.sys.BaseEnum;
import lombok.Getter;
import lombok.Setter;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.ser.std.SimpleBeanPropertyFilter;
import tools.jackson.databind.ser.std.SimpleFilterProvider;

/**
 * 返回结果实体
 * @author farkle
 * @date 2022-04-15
 */
@Getter @Setter
public class Result<T> {
    /** 成功消息  */
    public static final String SUCCESS_MESSAGE = "操作成功";
    /** 错误消息 */
    public static final String ERROR_MESSAGE = "操作失败";
    public static final Integer SUCCESS_CODE =  1;
    public static final Integer ERROR_CODE =  0;

    private int code;
    private String message;
    private T data;

    public Result(){}
    public Result(int code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public boolean successful(){
        return this.code == SUCCESS_CODE;
    }

    public static Result<Void> success(){
        return new Result<>(SUCCESS_CODE,SUCCESS_MESSAGE,null);
    }
    public static Result<Void> success(String message){
        return new Result<>(SUCCESS_CODE,message,null);
    }

    public static <T> Result<T> error(){
        return new Result<>(ERROR_CODE,ERROR_MESSAGE,null);
    }
    public static <T> Result<T> error(String message){
        return new Result<>(ERROR_CODE,message,null);
    }

    public static Result<Void> result(int code, String message){
        return new Result<>(code, message, null);
    }
    public static Result<Void> result(BaseEnum baseEnum){
        return new Result<>(baseEnum.getCode(),baseEnum.getDesc(),null);
    }
    public static Result<Void> result(int updateCount){
        return updateCount>0 ?success() :error();
    }

    public static <T> Result<T> success(T t){
        return new Result<>(SUCCESS_CODE,"",t);
    }
    public static Result<String> string(String data){
        return new Result<>(SUCCESS_CODE,"",data);
    }


    public String includeJson(Class<?> cls, String... fields){
        return Result.includeJson(this,cls,fields);
    }
    public String excludeJson(Class<?> cls, String... fields){
        return Result.excludeJson(this,cls,fields);
    }
    public static String includeJson(Object data, Class<?> cls, String... fields){
        JsonMapper jsonMapper = JsonUtil.builder()
                .addMixIn(cls, GlobalFilter.class)
                .filterProvider(new SimpleFilterProvider().addFilter(GlobalFilter.FILTER, SimpleBeanPropertyFilter.filterOutAllExcept(fields)))
                .build();
        return jsonMapper.writeValueAsString(data);
    }
    public static String excludeJson(Object data, Class<?> cls, String... fields){
        JsonMapper jsonMapper = JsonUtil.builder()
                .addMixIn(cls, GlobalFilter.class)
                .filterProvider(new SimpleFilterProvider().addFilter(GlobalFilter.FILTER, SimpleBeanPropertyFilter.serializeAllExcept(fields)))
                .build();
        return jsonMapper.writeValueAsString(data);
    }
}
