package com.hfw.model.entity;

/**
 * 通用异常
 * @author farkle
 * @date 2022-04-06
 */
public class GeneralException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private int code;

    public GeneralException(Throwable cause) {
        super(cause);
    }
    public GeneralException(String msg){
        super(msg);
        this.code = 0;
    }
    public GeneralException(int code, String msg){
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

}
