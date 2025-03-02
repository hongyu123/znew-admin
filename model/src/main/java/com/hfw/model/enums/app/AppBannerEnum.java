package com.hfw.model.enums.app;

import com.hfw.model.enums.BaseEnum;

/**
 * 轮播图枚举
 * @author farkle
 * @date 2023-01-29
 */
public enum AppBannerEnum implements BaseEnum {
    no_link(1,"无跳转"),
    url_link(2,"外部链接"),
    content(3,"图文详情")
    ;

    private final int code;
    private final String desc;
    AppBannerEnum(int code, String desc) {
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
