package com.hfw.model.po.sys;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EditLog {
    String value() default "";
}
