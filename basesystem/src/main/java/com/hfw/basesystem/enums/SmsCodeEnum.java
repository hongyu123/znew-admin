package com.hfw.basesystem.enums;

/**
 * 短信验证码类型
 * @author farkle
 * @date 2022-12-28
 */
public enum SmsCodeEnum {
    regist(1,"注册")
    ,login(2, "登录")
    ,valid_phone(3, "校验手机号码")
    ,edit_phone(4, "修改手机号码")
    ;

    public static final String redis_key = "SendMsg:";
    private final int code;
    private final String desc;
    SmsCodeEnum(int code, String desc) {
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
