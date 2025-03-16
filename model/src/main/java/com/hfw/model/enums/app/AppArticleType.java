package com.hfw.model.enums.app;

import com.hfw.model.enums.sys.BaseEnum;

/**
 * @author farkle
 * @date 2022-12-20
 */
public enum AppArticleType implements BaseEnum {
    system(1,"系统文章"),
    demo(2,"示例文章")
    ;

    private final int code;
    private final String desc;
    AppArticleType(int code, String desc) {
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
