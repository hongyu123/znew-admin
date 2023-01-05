package com.hfw.basesystem.gen;

import com.hfw.common.util.StrUtil;
import lombok.Data;

/**
 * 数据库字段
 * @author zyh
 * @date 2022-04-15
 */
@Data
public class Column {
    /** 数据库字段名 */
    private String columnName;
    /** java属性字段名 */
    private String fieldName;
    /**
     * java属性字段名首字母大写
     * @return
     */
    public String fieldNameFirstUpper(){
        return StrUtil.upperCase(this.fieldName);
    }

    /** 数据库字段类型 */
    private String dataType;
    /** java字段类型 */
    private String javaType;

    /** 字符串长度 */
    private Integer characterMaximumLength;
    /** 是否为空,YES/NO */
    private String isNullable;
    /** 索引类型,PRI/UNI */
    private String columnKey;
    /** 默认值 */
    private String columnDefault;
    /** 注释 */
    private String columnComment;
    /** 字段排序 */
    private Integer ordinalPosition;
}