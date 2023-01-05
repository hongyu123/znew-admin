package com.hfw.admin.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hfw.admin.service.SysDemoService;
import com.hfw.admin.mapper.SysDemoMapper;
import com.hfw.basesystem.entity.SysPicture;
import com.hfw.basesystem.enums.PictureEnum;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.model.entity.SysDemo;
import com.hfw.common.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 系统示例表服务实现
 * @author 
 * @date 2023-01-04
 */
@Service
public class SysDemoServiceImpl implements SysDemoService {

    @Autowired
    private SysDemoMapper sysDemoMapper;
    @Autowired
    private CommonDao commonDao;

    @Override
    public PageResult<SysDemo> page(SysDemo sysDemo) {
        PageResult<SysDemo> page = new PageResult<>(sysDemo);
        page.startPage();
        List<SysDemo> list = sysDemoMapper.list(sysDemo);
        page.setList(list);
        return page;
    }

    public SysDemo detail(Long id){
        SysDemo sysDemo = commonDao.findByPk(SysDemo.class, id);
        /*if(StringUtils.hasText(sysDemo.getInterest())){
            sysDemo.setInterestList(JSON.parseArray(sysDemo.getInterest(),String.class));
        }*/
        sysDemo.setPictureList( commonDao.list(new SysPicture().setTargetId(sysDemo.getId()).setType(PictureEnum.demo)) );
        return sysDemo;
    }
    public void add(SysDemo sysDemo){
        if(!CollectionUtils.isEmpty(sysDemo.getInterestList())){
            sysDemo.setInterest(JSON.toJSONString(sysDemo.getInterestList()));
        }
        commonDao.insert(sysDemo);
        if(!CollectionUtils.isEmpty(sysDemo.getPictureList())){
            sysDemo.getPictureList().forEach( pic->pic.setType(PictureEnum.demo).setTargetId(sysDemo.getId()));
            commonDao.insertBatch(sysDemo.getPictureList());
        }
    }
    public void edit(SysDemo sysDemo){
        if(!CollectionUtils.isEmpty(sysDemo.getInterestList())){
            sysDemo.setInterest(JSON.toJSONString(sysDemo.getInterestList()));
        }
        commonDao.updateByPk(sysDemo);
        if(!CollectionUtils.isEmpty(sysDemo.getPictureList())){
            commonDao.delete( new SysPicture().setTargetId(sysDemo.getId()).setType(PictureEnum.demo) );
            sysDemo.getPictureList().forEach( pic->pic.setType(PictureEnum.demo).setTargetId(sysDemo.getId()));
            commonDao.insertBatch(sysDemo.getPictureList());
        }
    }
    public void del(Long id){
        commonDao.delete( new SysPicture().setTargetId(id).setType(PictureEnum.demo) );
        commonDao.deleteByPk(SysDemo.class, id);
    }

}