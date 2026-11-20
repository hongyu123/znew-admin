package com.hfw.model.enums.sys;

/**
 * 权限类型
 * @author farkle
 * @date 2022-12-14
 */
public enum SysAuthEnum implements BaseEnum {
    Menu(1,"菜单")
    ,Dir(2, "目录")
    ,Button(3, "按钮")
    ,Auth(4, "权限")
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