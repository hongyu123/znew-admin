package com.hfw.basesystem.enums;

import com.hfw.common.enums.IEnum;

/**
 * 登录类型
 * @author zyh
 * @date 2022-12-17
 */
public enum LogoutType implements IEnum {
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