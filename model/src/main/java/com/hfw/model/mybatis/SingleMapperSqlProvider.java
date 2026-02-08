package com.hfw.model.mybatis;

import com.hfw.model.mybatis.anno.UpdateStrategy;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SingleMapperSqlProvider {
    private SqlHelper sqlHelper = MybatisGlobalConfig.getSqlHelper();

    private void assertWhere(ProviderContext context, Where<?> where) throws Exception{
        List<? extends Where.Condition<?>> condList = where.getCondList();
        if(condList==null || condList.isEmpty()){
            throw new SQLException(context.getMapperType()+"."+context.getMapperMethod().getName()+" where条件不允许为空");
        }
    }

    public String selectByPk(@Param("type")Class<?> type, @Param("pk") Serializable pk) throws Exception {
        return sqlHelper.selectByPk(type);
    }

    public String selectList(@Param("type")Class<?> type, @Param("where") Where<?> where) throws Exception{
        return sqlHelper.selectList(type, where);
    }

    public String count(@Param("type")Class<?> type, @Param("where")Where<?> where) throws Exception{
        return sqlHelper.count(type, where);
    }

    public String insert(Object entity) throws Exception{
        return sqlHelper.insert(entity);
    }

    public String insertBatch(ProviderContext context, List<?> list) throws Exception{
        if(list==null || list.isEmpty()){
            throw new SQLException(context.getMapperType()+" "+context.getMapperMethod().getName()+"参数为空");
        }
        return sqlHelper.insertBatch(list);
    }

    public String updateByPk(Map<String,Object> params) throws Exception{
        return sqlHelper.updateByPk(params.get("entity"), UpdateStrategy.NotEmpty, null);
    }

    public String updateStrategyByPk(@Param("entity")Object entity, @Param("updateStrategy")UpdateStrategy updateStrategy, @Param("nullUpdateColumns") Column<?>[] nullUpdateColumns) throws Exception{
        return sqlHelper.updateByPk(entity, updateStrategy, nullUpdateColumns);
    }

    public String update(ProviderContext context, @Param("entity")Object entity, @Param("where")Where<?> where) throws Exception{
        this.assertWhere(context, where);
        return sqlHelper.update(entity, where);
    }

    public String deleteByPk(@Param("type")Class<?> type, @Param("pk")Serializable pk) throws Exception {
        return sqlHelper.deleteByPk(type);
    }

    public String deleteByPks(@Param("type")Class<?> type, @Param("pks")Collection<? extends Serializable> pks) throws Exception {
        return sqlHelper.deleteByPks(type, pks);
    }

    public String delete(ProviderContext context, @Param("type")Class<?> type, @Param("where")Where<?> where) throws Exception{
        this.assertWhere(context, where);
        return sqlHelper.delete(type, where);
    }

}
