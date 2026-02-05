package com.hfw.model.mybatis.anno;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface LogicDelete {

    //删除前/未删除的值
    String beforeValue() default "0";

    //删除后的值
    String deletedValue() default "1";

}
