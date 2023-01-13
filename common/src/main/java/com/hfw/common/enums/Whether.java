package com.hfw.common.enums;

/**
 * 是否
 * @author farkle
 * @date 2022-12-28
 */
public enum Whether implements IEnum {
    Yes(1,"是"),
    No(0,"否");

    private final int code;
    private final String desc;
    private Whether(int code,String desc) {
        this.code = code;
        this.desc= desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }
    @Override
    public String getDesc() {
        return this.desc;
    }
}
