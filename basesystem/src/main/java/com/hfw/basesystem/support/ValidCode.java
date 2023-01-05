package com.hfw.basesystem.support;

import com.hfw.common.enums.IEnum;

/**
 * 错误代码
 * @author zyh
 * @date 2022-04-16
 */
public enum ValidCode implements IEnum {
    NO_LOGIN(100,"登录失效,请重新登录!")
    ,DISABLE_ACCOUNT(101,"被禁用的账号,请联系管理员!")
    ,permission_denied(403,"permission denied(权限不足!!)")
    ;

    private final int code;
    private final String desc;
    private ValidCode(int code, String desc) {
        this.code = code;
        this.desc= desc;
    }
    public int getCode() {
        return this.code;
    }
    public String getDesc() {
        return this.desc;
    }
}
