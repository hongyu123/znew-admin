package com.hfw.admin.service.impl;

import com.alibaba.fastjson2.JSON;
import com.hfw.admin.mapper.SysDemoMapper;
import com.hfw.admin.service.SysDemoService;
import com.hfw.basesystem.enums.PictureEnum;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.SysPictureService;
import com.hfw.common.entity.PageResult;
import com.hfw.model.entity.SysDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    @Autowired
    private SysPictureService sysPictureService;

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
        sysDemo.setPictureList( sysPictureService.list(id,PictureEnum.demo) );
        return sysDemo;
    }
    public void add(SysDemo sysDemo){
        if(!CollectionUtils.isEmpty(sysDemo.getInterestList())){
            sysDemo.setInterest(JSON.toJSONString(sysDemo.getInterestList()));
        }
        commonDao.insert(sysDemo);
        sysPictureService.save(sysDemo.getId(),PictureEnum.demo, sysDemo.getPictureList());
    }
    public void edit(SysDemo sysDemo){
        if(!CollectionUtils.isEmpty(sysDemo.getInterestList())){
            sysDemo.setInterest(JSON.toJSONString(sysDemo.getInterestList()));
        }
        commonDao.updateByPk(sysDemo);
        sysPictureService.edit(sysDemo.getId(),PictureEnum.demo, sysDemo.getPictureList());
    }
    public void del(Long id){
        commonDao.deleteByPk(SysDemo.class, id);
        sysPictureService.del(id,PictureEnum.demo);
    }

}