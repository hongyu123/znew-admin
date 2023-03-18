package com.hfw.admin.service.impl;

import com.alibaba.fastjson2.JSON;
import com.hfw.admin.easyexcel.DataHandleEnum;
import com.hfw.admin.easyexcel.ExtListener;
import com.hfw.admin.mapper.SysDemoMapper;
import com.hfw.admin.monitor.Sys;
import com.hfw.admin.service.SysDemoService;
import com.hfw.basesystem.enums.PictureEnum;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.SysPictureService;
import com.hfw.common.entity.PageResult;
import com.hfw.common.util.BeanUtil;
import com.hfw.model.entity.SysDemo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统示例表服务实现
 * @author 
 * @date 2023-01-04
 */
@Service("sysDemoService")
public class SysDemoServiceImpl implements SysDemoService {

    @Resource
    private SysDemoMapper sysDemoMapper;
    @Resource
    private CommonDao commonDao;
    @Resource
    private SysPictureService sysPictureService;

    @Override
    public PageResult<SysDemo> page(SysDemo sysDemo) {
        PageResult<SysDemo> page = new PageResult<>(sysDemo);
        page.startPage();
        List<SysDemo> list = sysDemoMapper.list(sysDemo);
        page.setList(list);
        return page;
    }
    @Override
    public List<SysDemo> list(SysDemo sysDemo){
        return sysDemoMapper.list(sysDemo);
    }

    public SysDemo detail(Long id){
        SysDemo sysDemo = commonDao.selectByPk(SysDemo.class, id);
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


    @Override
    public void validData(int index, SysDemo demo, ExtListener<SysDemo> listener){
        if(!StringUtils.hasText(demo.getName())){
            listener.validFail(index,"name", "姓名不能为空", demo);
        }
    }

    @Override
    public int insert(List<SysDemo> list){
        return commonDao.insertBatch(list);
    }
    @Override
    public int cover(List<SysDemo> list, boolean update){
        List<Long> delIds = new ArrayList<>();
        for(SysDemo demo : list){
            SysDemo origin = commonDao.selectOne(SysDemo.builder().name(demo.getName()).build());
            if(origin!=null){
                delIds.add(origin.getId());
                demo.setId(origin.getId());//注意逻辑删除不能直接设置id
                //更新导入
                if(update){
                    BeanUtil.copyNotNullProperties(origin, demo);
                }
            }
        }
        commonDao.deleteBatch(SysDemo.class, delIds);
        return commonDao.insertBatch(list);
    }

}