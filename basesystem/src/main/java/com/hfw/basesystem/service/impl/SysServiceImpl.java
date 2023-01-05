package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.entity.SysContent;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zyh
 * @date 2022-11-29
 */
@Service
public class SysServiceImpl implements SysService {

    @Autowired
    private CommonDao commonDao;

    @Override
    public SysContent sysContent(Long id){
        return commonDao.findByPk(SysContent.class, id);
    }

    @Override
    public SysContent sysContent(String content){
        SysContent sysContent = new SysContent().setContent(content);
        commonDao.insert(sysContent);
        return sysContent;
    }

    @Override
    public int sysContent(SysContent content){
        return commonDao.updateByPk(content);
    }

    @Override
    public int delSysContent(Long id){
        return commonDao.deleteByPk(SysContent.class, id);
    }
}
