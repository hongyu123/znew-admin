package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.entity.SysContent;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.SysContentService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 系统图文服务
 * @author farkle
 * @date 2022-11-29
 */
@Service("sysContentService")
public class SysContentServiceImpl implements SysContentService {

    @Resource
    private CommonDao commonDao;

    @Override
    public SysContent detail(Long id){
        return commonDao.selectByPk(SysContent.class, id);
    }
    @Override
    public String content(Long id){
        if(id==null || id<=0){
            return "";
        }
        SysContent sysContent = this.detail(id);
        return sysContent==null ?"":sysContent.getContent();
    }

    @Override
    public Long save(String content){
        SysContent sysContent = new SysContent().setContent(content);
        commonDao.insert(sysContent);
        return sysContent.getId();
    }

    @Override
    public int edit(Long id, String content){
        return commonDao.updateByPk(new SysContent().setId(id).setContent(content));
    }

    @Override
    public int del(Long id){
        return commonDao.deleteByPk(SysContent.class, id);
    }
}
