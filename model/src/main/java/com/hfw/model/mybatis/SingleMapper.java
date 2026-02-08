package com.hfw.model.mybatis;

import com.hfw.model.mybatis.anno.UpdateStrategy;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface SingleMapper {

    @SelectProvider(value = SingleMapperSqlProvider.class, method = "selectByPk")
    <T> T selectByPk(@Param("type") Class<T> type, @Param("pk") Serializable pk);

    default <T> T selectOne(Class<T> type, Where<T> where){
        List<T> list = this.selectList(type, where);
        if(list!=null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @SelectProvider(value = SingleMapperSqlProvider.class, method = "selectList")
    <T> List<T> selectList(@Param("type") Class<T> type, @Param("where") Where<T> where);

    @SelectProvider(value = SingleMapperSqlProvider.class, method = "count")
    <T> long count(@Param("type") Class<T> type, @Param("where") Where<T> where);

    @InsertProvider(value = SingleMapperSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Object entity);

    @InsertProvider(value = SingleMapperSqlProvider.class, method = "insertBatch")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertBatch(List<?> list);

    @UpdateProvider(value = SingleMapperSqlProvider.class, method = "updateByPk")
    int updateByPk(@Param("entity")Object entity);

    @UpdateProvider(value = SingleMapperSqlProvider.class, method = "updateStrategyByPk")
    int updateStByPk(@Param("entity")Object entity, @Param("updateStrategy") UpdateStrategy updateStrategy, @Param("nullUpdateColumns")Column<?> ... nullUpdateColumns);

    @UpdateProvider(value = SingleMapperSqlProvider.class, method = "update")
    <T> int update(@Param("entity")T entity, @Param("where")Where<T> where);

    @DeleteProvider(value = SingleMapperSqlProvider.class, method = "deleteByPk")
    int deleteByPk(@Param("type") Class<?> type, @Param("pk")Serializable pk);

    @DeleteProvider(value = SingleMapperSqlProvider.class, method = "deleteByPks")
    int deleteByPks(@Param("type") Class<?> type, @Param("pks")Collection<? extends Serializable> pks);

    @DeleteProvider(value = SingleMapperSqlProvider.class, method = "delete")
    int delete(@Param("type") Class<?> type, @Param("where")Where<?> where);
}
