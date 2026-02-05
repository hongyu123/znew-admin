package com.hfw.model.mybatis;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TableInfo {
    private Class<?> tableClass;
    /**
     * JAVA字段名 -> 列信息的映射
     */
    private Map<String,ColumnInfo> columnMap;
    private String tableName;
    private String schema;
    public String tableName(){
        if(schema!=null && !schema.isBlank()){
            return schema+"."+tableName;
        }
        return tableName;
    }
    private String selectColumns;

    private ColumnInfo pk;
    private ColumnInfo logicDelete;
}
