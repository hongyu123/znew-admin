package com.hfw.service.component;


import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.mybatis.Column;
import com.hfw.model.mybatis.Where;
import com.hfw.model.mybatis.anno.UpdateStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Service
public class CommonService {
    @Autowired
    private CommonMapper commonMapper;

    public <T> T selectByPk(Class<T> type, Serializable pk){
        return commonMapper.selectByPk(type, pk);
    }

    public <T> T selectOne(Class<T> type, Where<T> where){
        return commonMapper.selectOne(type, where);
    }

    public <T> List<T> selectList(Class<T> type, Where<T> where){
        return commonMapper.selectList(type, where);
    }

    public <T> long count(Class<T> type, Where<T> where){
        return commonMapper.count(type, where);
    }
    public <T> PageResult<T> page(Class<T> type, Where<T> where, Page<T> page){
        PageResult<T> pageResult = new PageResult<>(page.getPageNumber(), page.getPageSize());
        long count = this.count(type, where);
        pageResult.setTotal(count);
        if(count>0){
            where.limit(page.getRows()).offset(page.getStart());
            pageResult.setData(this.selectList(type, where));
        }
        return pageResult;
    }

    public int insert(Object entity){
        return commonMapper.insert(entity);
    }

    public int insertBatch(List<?> list){
        return commonMapper.insertBatch(list);
    }

    public int updateByPk(Object entity){
        return commonMapper.updateByPk(entity);
    }

    public int updateStByPk(Object entity, UpdateStrategy updateStrategy, Column<?>... nullUpdateColumns){
        return commonMapper.updateStByPk(entity, updateStrategy, nullUpdateColumns);
    }

    public <T> int update(T entity, Where<T> where){
        return commonMapper.update(entity, where);
    }

    public int deleteByPk(Class<?> type,  Serializable pk){
        return commonMapper.deleteByPk(type, pk);
    }

    public int deleteByPks(Class<?> type, Collection<? extends Serializable> pks){
        return commonMapper.deleteByPks(type, pks);
    }

    public int delete(Class<?> type, Where<?> where){
        return commonMapper.delete(type, where);
    }

}
