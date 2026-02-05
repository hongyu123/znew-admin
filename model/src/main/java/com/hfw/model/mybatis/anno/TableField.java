package com.hfw.model.mybatis.anno;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableField {

    String value() default "";

    boolean exist() default true;

    //新增自动填充
    String insertValue() default "";

    //更新自动填充
    String updateValue() default "";

}

