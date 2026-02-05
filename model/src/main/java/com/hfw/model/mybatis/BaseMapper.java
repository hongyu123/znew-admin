package com.hfw.model.mybatis;

import com.hfw.model.mybatis.anno.UpdateStrategy;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BaseMapper<T> {

    @SelectProvider(value = SqlProvider.class, method = "selectByPk")
    T selectByPk(Serializable pk);

    default T selectOne(Where<T> where){
        List<T> list = this.selectList(where);
        if(list!=null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    @SelectProvider(value = SqlProvider.class, method = "selectList")
    List<T> selectList(Where<T> where);

    @SelectProvider(value = SqlProvider.class, method = "count")
    long count(Where<T> where);

    @InsertProvider(value = SqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(T t);

    @InsertProvider(value = SqlProvider.class, method = "insertBatch")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertBatch(List<T> list);

    @UpdateProvider(value = SqlProvider.class, method = "updateByPk")
    int updateByPk(@Param("entity")T entity);

    @UpdateProvider(value = SqlProvider.class, method = "updateStrategyByPk")
    int updateStByPk(@Param("entity")T entity, @Param("updateStrategy")UpdateStrategy updateStrategy, @Param("nullUpdateColumns")Column<?> ... nullUpdateColumns);

    @UpdateProvider(value = SqlProvider.class, method = "update")
    int update(@Param("entity")T entity, @Param("where")Where<T> where);

    @DeleteProvider(value = SqlProvider.class, method = "deleteByPk")
    int deleteByPk(Serializable pk);

    @DeleteProvider(value = SqlProvider.class, method = "deleteByPks")
    int deleteByPks(Collection<? extends Serializable> pks);

    @DeleteProvider(value = SqlProvider.class, method = "delete")
    int delete(Where<T> where);

}
