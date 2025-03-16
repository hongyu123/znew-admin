package com.hfw.model.enums.app;

import com.hfw.model.enums.sys.BaseEnum;

/**
 * app文章
 * @author farkle
 * @date 2022-11-25
 */
public interface AppArticleEnum {

    /**
     * 系统文章
     */
    enum System{
        user_agreement(1L,"用户协议")
        ,privacy_policy(2L, "隐私政策")
        ,about_us(3L, "关于我们")
        ;

        private final Long code;
        private final String desc;
        System(Long code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Long getCode() {
            return this.code;
        }
        public String getDesc() {
            return this.desc;
        }
    }

    /**
     * 文章类型
     */
    enum ContentType implements BaseEnum {
        content(1,"图文内容")
        ,link(2, "超链接")
        ;

        private final int code;
        private final String desc;
        ContentType(int code, String desc) {
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
}
