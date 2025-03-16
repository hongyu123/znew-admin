package com.hfw.model.enums.sys;

/**
 * 登录类型
 * @author farkle
 * @date 2022-12-17
 */
public enum LogoutType implements BaseEnum {
    expire(1,"会话过期")
    ,logout(2, "用户登出")
    ,pushed_off(3, "被挤下线")
    ;

    private final int code;
    private final String desc;
    LogoutType(int code, String desc) {
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