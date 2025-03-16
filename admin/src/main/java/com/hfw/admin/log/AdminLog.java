package com.hfw.admin.log;

import java.lang.annotation.*;

/**
 * 后台操作日志记录注解
 * @author farkle
 * @date 2022-08-03
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminLog {
    String value() default "";
}
