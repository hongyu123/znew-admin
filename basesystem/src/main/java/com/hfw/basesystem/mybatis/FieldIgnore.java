package com.hfw.basesystem.mybatis;

import java.lang.annotation.*;

/**
 * @author zyh
 * @date 2022-06-14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface FieldIgnore {
}