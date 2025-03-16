package com.hfw.model.enums.app;

import com.hfw.model.enums.sys.BaseEnum;

/**
 * 设备类型
 * @author farkle
 * @date 2022-12-28
 */
public enum DeviceEnum implements BaseEnum {
    android(1,"android")
    ,ios(2, "ios")
    ;

    private final int code;
    private final String desc;
    DeviceEnum(int code, String desc) {
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