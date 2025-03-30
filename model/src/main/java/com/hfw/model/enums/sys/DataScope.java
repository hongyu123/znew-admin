package com.hfw.model.enums.sys;

/**
 * 数据权限类型
 */
public enum DataScope implements BaseEnum {
    OrganizationAndChildren(1,"当前部门及子部门"),
    Organization(2, "当前部门"),
    User(3, "当前用户"),
    Custom(4, "自定义部门");

    private final int code;
    private final String desc;
    private DataScope(int code, String desc) {
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
