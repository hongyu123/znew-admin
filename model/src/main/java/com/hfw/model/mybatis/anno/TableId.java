package com.hfw.model.mybatis.anno;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableId {
    IdType value() default IdType.AUTO_INCREMENT;
}
