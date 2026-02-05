package com.hfw.model.mybatis;

import com.hfw.model.mybatis.anno.IdType;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

@Getter
@Setter
public class ColumnInfo {
    private String columnName;
    private String fieldName;
    private Field field;
    //主键
    private Boolean pk = false;
    private IdType idType;
    //逻辑删除
    private Boolean logicDelete = false;
    private String beforeValue = "0";
    private String deletedValue = "1";
    //字段填充
    private String insertValue;
    private String updateValue;
    public boolean insertFill(){
        return insertValue!=null && !insertValue.isBlank();
    }
    public boolean updateFill(){
        return updateValue!=null && !updateValue.isBlank();
    }

}
