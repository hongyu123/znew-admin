package com.hfw.basesystem.enums;

import com.hfw.common.enums.IEnum;

/**
 * 权限类型
 * @author farkle
 * @date 2022-12-14
 */
public enum SysAuthEnum implements IEnum {
    menu(1,"菜单")
    ,dir(2, "目录")
    ,button(3, "按钮")
    ,auth(4, "权限")
    ;

    private final int code;
    private final String desc;
    SysAuthEnum(int code, String desc) {
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