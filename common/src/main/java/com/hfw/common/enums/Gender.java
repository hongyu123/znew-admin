package com.hfw.common.enums;

/**
 * 性别
 * @author zyh
 * @date 2022-12-28
 */
public enum Gender implements IEnum {
    Male(1,"男")
    ,Female(2, "女")
    ,Unknown(3, "未知")
    ;

    private final int code;
    private final String desc;
    private Gender(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }
    public String getDesc() {
        return this.desc;
    }
    public static Gender of(Integer code) {
        if(code==null){
            return Unknown;
        }
        for (Gender s : Gender.values()) {
            if (s.code == code) {
                return s;
            }
        }
        return Unknown;
    }
}
