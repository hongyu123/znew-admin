package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.entity.SysConfig;
import com.hfw.basesystem.mapper.SysMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.SysConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 属性配置服务
 * @author farkle
 * @date 2023-01-29
 */
@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {

    //缓存
    private static Map<String,String> cache = new ConcurrentHashMap<>();

    @Resource
    private CommonDao commonDao;
    @Resource
    private SysMapper sysMapper;

    @Override
    public String get(String key){
        String value = cache.get(key);
        if(value==null){
            SysConfig sysConfig = commonDao.selectOne(new SysConfig().setKey(key));
            if(sysConfig!=null){
                value = sysConfig.getValue();
                cache.put(key,value);
            }
        }
        return value;
    }

    @Override
    public void set(String key, String value){
        this.set(key,value,null);
    }

    @Override
    public void set(String key, String value, String comment){
        cache.remove(key);
        int res = sysMapper.config(key,value);
        if(res<=0){
            SysConfig sysConfig = new SysConfig().setKey(key).setValue(value).setComment(comment);
            commonDao.insert(sysConfig);
        }
        cache.remove(key);
    }

}
