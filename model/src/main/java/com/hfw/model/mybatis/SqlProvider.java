package com.hfw.model.mybatis;

import com.hfw.model.mybatis.anno.UpdateStrategy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SqlProvider {
    private SqlHelper sqlHelper = MybatisGlobalConfig.getSqlHelper();

    private Class<?> getTableClass(ProviderContext context) throws SQLException {
        Type[] types = context.getMapperType().getGenericInterfaces();
        for (Type type : types){
            if(type instanceof ParameterizedType t){
                Type[] typeArgs = t.getActualTypeArguments();
                if(t.getRawType()==BaseMapper.class && typeArgs.length==1) {
                    return (Class<?>)typeArgs[0];
                }
            }
        }
        throw new SQLException("无法识别Mapper对应的实体类:"+context.getMapperType());
    }
    private void assertWhere(ProviderContext context, Where<?> where) throws Exception{
        List<? extends Where.Condition<?>> condList = where.getCondList();
        if(condList==null || condList.isEmpty()){
            throw new SQLException(context.getMapperType()+"."+context.getMapperMethod().getName()+" where条件不允许为空");
        }
    }

    public String selectByPk(ProviderContext context, @Param("pk")Serializable pk) throws Exception {
        Class<?> tableClass = this.getTableClass(context);
        return sqlHelper.selectByPk(tableClass);
    }

    public String selectList(ProviderContext context, @Param("where")Where<?> where) throws Exception{
        Class<?> tableClass = this.getTableClass(context);
        return sqlHelper.selectList(tableClass, where);
    }

    public String count(ProviderContext context, @Param("where")Where<?> where) throws Exception{
        Class<?> tableClass = this.getTableClass(context);
        return sqlHelper.count(tableClass, where);
    }

    public String insert(ProviderContext context, Object entity) throws Exception{
        return sqlHelper.insert(entity);
    }

    public String insertBatch(ProviderContext context, List<?> list) throws Exception{
        if(list==null || list.isEmpty()){
            throw new SQLException(context.getMapperType()+" "+context.getMapperMethod().getName()+"参数为空");
        }
        return sqlHelper.insertBatch(list);
    }

    public String updateByPk(ProviderContext context, Map<String,Object> params) throws Exception{
        return sqlHelper.updateByPk(params.get("entity"), UpdateStrategy.NotEmpty, null);
    }

    public String updateStrategyByPk(ProviderContext context, @Param("entity")Object entity, @Param("updateStrategy")UpdateStrategy updateStrategy, @Param("nullUpdateColumns") Column<?>[] nullUpdateColumns) throws Exception{
        return sqlHelper.updateByPk(entity, updateStrategy, nullUpdateColumns);
    }

    public String update(ProviderContext context, @Param("entity")Object entity, @Param("where")Where<?> where) throws Exception{
        this.assertWhere(context, where);
        return sqlHelper.update(entity, where);
    }

    public String deleteByPk(ProviderContext context, @Param("pk")Serializable pk) throws Exception {
        Class<?> tableClass = this.getTableClass(context);
        return sqlHelper.deleteByPk(tableClass);
    }

    public String deleteByPks(ProviderContext context, @Param("pks")Collection<? extends Serializable> pks) throws Exception {
        Class<?> tableClass = this.getTableClass(context);
        return sqlHelper.deleteByPks(tableClass, pks);
    }

    public String delete(ProviderContext context, @Param("where")Where<?> where) throws Exception{
        this.assertWhere(context, where);
        Class<?> tableClass = this.getTableClass(context);
        return sqlHelper.delete(tableClass, where);
    }

}
