package com.hfw.basesystem.enums;

import com.hfw.common.enums.IEnum;

/**
 * 收支类型
 * @author farkle
 * @date 2023-01-11
 */
public enum PaymentsType implements IEnum {
    income(1,"收入")
    ,expenses(2, "支出")
    ;

    private final int code;
    private final String desc;
    PaymentsType(int code, String desc) {
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