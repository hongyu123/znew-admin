package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通用服务
 * @author farkle
 * @date 2022-12-08
 */
@Service
public class CommonServiceImpl<T> implements CommonService<T> {

    @Autowired
    private CommonDao commonDao;

    public List<T> list(T t){
        return commonDao.list(t);
    }
    public T detail(Class<T> clazz, Long id){
        return commonDao.findByPk(clazz, id);
    }
    public Long count(T t){
        return commonDao.count(t);
    }
    public int save(T t){
        return commonDao.insert(t);
    }
    public int edit(T t){
        return commonDao.updateByPk(t);
    }
    public int update(T t, T cond){
        return  commonDao.update(t,cond);
    }
    public int del(Class<T> clazz, Long id){
        return commonDao.deleteByPk(clazz, id);
    }
    public int dels(Class<T> clazz, List<Long> ids){
        return commonDao.deleteBatch(clazz, ids);
    }

}
