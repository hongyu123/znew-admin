package com.hfw.model.jackson;

import com.hfw.model.enums.BaseEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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

    public Result(int code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public boolean successful(){
        return this.code == SUCCESS_CODE;
    }

    public static Result<String> message(int code, String message){
        return new Result<>(code, message, "");
    }
    public static Result<String> message(BaseEnum baseEnum){
        return new Result<>(baseEnum.getCode(),baseEnum.getDesc(),"");
    }

    public static Result<String> success(){
        return new Result<>(SUCCESS_CODE,SUCCESS_MESSAGE,"");
    }
    public static Result<String> success(String message){
        return new Result<>(SUCCESS_CODE,message,"");
    }
    public static <T> Result<T> success(T t){
        return new Result<>(SUCCESS_CODE,"",t);
    }
    public static <T> Result<T> success(int code, T t){
        return new Result<>(code,"",t);
    }
    public static Result<String> string(String data){
        if(data==null){
            data = "";
        }
        return new Result<>(SUCCESS_CODE,"",data);
    }
    public static Result<List<?>> list(List<?> list){
        if(list == null){
            list = new ArrayList<>();
        }
        return new Result<>(SUCCESS_CODE,"",list);
    }
    public static Result<String> error(String message){
        return new Result<>(ERROR_CODE,message,"");
    }

}
