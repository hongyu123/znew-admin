package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.CommonService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通用服务
 * @author farkle
 * @date 2022-12-08
 */
@Service("commonService")
public class CommonServiceImpl<T> implements CommonService<T> {
    @Resource
    private CommonDao commonDao;

    public List<T> listAll(Class<T> clazz){
        return commonDao.select(clazz);
    }
    public List<T> list(T t){
        return commonDao.select(t);
    }
    public List<T> list(T t, Integer pageNumber, Integer pageSize){
        return commonDao.select(t,pageNumber,pageSize);
    }
    public <T> T getOne(T t){
        return commonDao.selectOne(t);
    }
    public T getById(Class<T> clazz, Long id){
        return commonDao.selectByPk(clazz, id);
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
    public int edit(T t, T cond){
        return  commonDao.update(t,cond);
    }
    public int del(Class<T> clazz, Long id){
        return commonDao.deleteByPk(clazz, id);
    }
    public int dels(Class<T> clazz, List<Long> ids){
        return commonDao.deleteBatch(clazz, ids);
    }

}
