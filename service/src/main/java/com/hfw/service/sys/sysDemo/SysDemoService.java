package com.hfw.service.sys.sysDemo;

import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDemoService {
    @Autowired
    private SysDemoMapper sysDemoMapper;

    public Page<SysDemo> page(Page<SysDemo> page, SysDemo po) {
        return sysDemoMapper.page(page, po);
    }
    public SysDemoDTO detail(Long id){
        return SysDemoDTO.fromPo(sysDemoMapper.getById(id));
    }

    public int add(SysDemoDTO dto){
        return sysDemoMapper.save(dto.toPo().saveFilter());
    }
    public int edit(SysDemoDTO dto){
        return sysDemoMapper.update(dto.toPo().updateFilter());
    }
    public int del(Long id){
        return sysDemoMapper.deleteById(id);
    }
    public int dels(List<Long> ids){
        return sysDemoMapper.deleteByIds(ids);
    }

}
