package com.hfw.basesystem.enums;

import com.hfw.common.enums.IEnum;

/**
 * 图片枚举类型
 * @author farkle
 * @date 2022-12-28
 */
public enum PictureEnum implements IEnum {
    demo(-1,"demo")
    ;

    private final int code;
    private final String desc;
    PictureEnum(int code, String desc) {
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
