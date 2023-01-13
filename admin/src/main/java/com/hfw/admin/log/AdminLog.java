package com.hfw.admin.log;

import java.lang.annotation.*;

/**
 * 后台登录日志
 * @author farkle
 * @date 2022-08-03
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminLog {
    String value() default "";
}
