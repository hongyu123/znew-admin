package com.hfw.model.mybatis;

import com.hfw.model.mybatis.anno.IdType;
import com.hfw.model.mybatis.typehandler.JacksonTypeHandler;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

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

    public Class<? extends TypeHandler> typeHandlerClass;

    private TypeHandler<?> resolveTypeHandler(Class<?> javaType, Class<? extends TypeHandler> typeHandlerType, Type type) {
        if (typeHandlerType == null || typeHandlerType == TypeHandler.class) {
            return null;
        }
        if(typeHandlerType == JacksonTypeHandler.class){
            try{
                Constructor<?> c = typeHandlerType.getConstructor(Class.class, Type.class);
                return (TypeHandler<?>) c.newInstance(javaType, type);
            }catch (Exception e){
                throw new TypeException("Failed invoking constructor for handler " + typeHandlerType, e);
            }
        }
        try{
            Constructor<?> c = typeHandlerType.getConstructor(Class.class);
            return (TypeHandler<?>) c.newInstance(field.getType());
        }catch (Exception e){
            throw new TypeException("Failed invoking constructor for handler " + typeHandlerType, e);
        }
    }

    public ResultMapping buildResultMapping(Configuration configuration){
        Class<?> type = field.getType();
        Type genericType = field.getGenericType();
        TypeHandler<?> typeHandler = this.resolveTypeHandler(type, typeHandlerClass, genericType);
        return new ResultMapping.Builder(configuration, fieldName, columnName, type)
//                .jdbcType(jdbcType)
//                .nestedQueryId(this.applyCurrentNamespace(nestedSelect, true))
//                .nestedResultMapId(this.applyCurrentNamespace(nestedResultMap, true))
//                .resultSet(resultSet)
                .typeHandler(typeHandler)
//                .flags((List)(flags == null ? new ArrayList() : flags))
//                .composites(composites)
//                .notNullColumns(this.parseMultipleColumnNames(notNullColumn))
//                .columnPrefix(columnPrefix)
//                .foreignColumn(foreignColumn)
//                .lazy(lazy)
                .build();

    }
    public String sqlSourceTypeHandler(){
        if (typeHandlerClass == null || typeHandlerClass == TypeHandler.class) {
            return "";
        }
        return ",typeHandler="+typeHandlerClass.getName();
    }

}
