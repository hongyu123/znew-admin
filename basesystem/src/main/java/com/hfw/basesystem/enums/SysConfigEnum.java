package com.hfw.basesystem.enums;

/**
 * 系统配置枚举
 * @author farkle
 * @date 2023-01-29
 */
public enum SysConfigEnum {
    phone("phone","联系电话")
    ;

    private final String code;
    private final String desc;
    SysConfigEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }
    public String getDesc() {
        return this.desc;
    }
}