package com.hfw.model.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hfw.model.enums.sys.BaseEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    public static final String INCLUDE_FILTER = "INCLUDE_FILTER";

    private int code;
    private String message;
    private T data;
    @JsonIgnore
    private DataType dataType;

    public Result(){}
    public Result(int code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Result(int code, String message, T data, DataType dataType){
        this.code = code;
        this.message = message;
        this.data = data;
        this.dataType = dataType;
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

    // 日期 枚举 Integer等基本类型请自行处理null, 否则同一返回{}
    public static <T> Result<T> success(T t){
        return new Result<>(SUCCESS_CODE,"",t, DataType.Obj);
    }
    public static <T> Result<T> success(int code, T t){
        return new Result<>(code,"",t, DataType.Obj);
    }
    public static Result<String> string(String data){
        return new Result<>(SUCCESS_CODE,"",data, DataType.String);
    }
    public static <T> Result<List<T>> success(List<T> list){
        return new Result<>(SUCCESS_CODE,"",list, DataType.List);
    }

}
