package com.hfw.common.enums;

/**
 * 启用状态
 * @author zyh
 * @date 2022-12-28
 */
public enum EnableState implements IEnum {
    Enable(1,"启用"),
    Disable(2, "禁用");

    private final int code;
    private final String desc;
    private EnableState(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }
    public String getDesc() {
        return this.desc;
    }
}