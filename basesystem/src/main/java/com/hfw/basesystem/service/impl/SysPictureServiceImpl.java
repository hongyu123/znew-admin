package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.entity.SysPicture;
import com.hfw.basesystem.enums.PictureEnum;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.SysPictureService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统图片服务
 * @author farkle
 * @date 2023-01-09
 */
@Service("sysPictureService")
public class SysPictureServiceImpl implements SysPictureService {

    @Resource
    private CommonDao commonDao;

    @Override
    public List<SysPicture> list(Long targetId, PictureEnum type){
        return commonDao.select(new SysPicture().setTargetId(targetId).setType(type));
    }

    @Override
    public int save(Long targetId, PictureEnum type, List<SysPicture> pictureList){
        if(pictureList==null || pictureList.size()<=0){
            return 0;
        }
        for(SysPicture pic : pictureList){
            pic.setTargetId(targetId);
            pic.setType(type);
        }
        return commonDao.insertBatch(pictureList);
    }

    @Override
    public int edit(Long targetId, PictureEnum type, List<SysPicture> pictureList){
        this.del(targetId,type);
        return this.save(targetId,type, pictureList);
    }

    @Override
    public int del(Long targetId, PictureEnum type){
        return commonDao.delete( new SysPicture().setTargetId(targetId).setType(type) );
    }

}
