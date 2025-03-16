//package com.hfw.service.znew.sysDemo;
//
//import com.hfw.model.entity.Page;
//import com.hfw.model.po.znew.SysDemo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * 系统示例表服务
// * @author farkle
// * @date 2025-03-16
// */
//@Service
//public class SysDemoService {
//    @Autowired
//    private SysDemoMapper sysDemoMapper;
//
//    public Page<SysDemo> page(Page<SysDemo> page, SysDemo po) {
//        return sysDemoMapper.page(page, po);
//    }
//    public SysDemo detail(Long id){
//        return sysDemoMapper.getById(id);
//    }
//
//    public int add(SysDemo sysDemo){
//        return sysDemoMapper.save(sysDemo);
//    }
//    public int edit(SysDemo sysDemo){
//        return sysDemoMapper.update(sysDemo);
//    }
//    public int del(Long id){
//        return sysDemoMapper.deleteById(id);
//    }
//    public int dels(List<Long> ids){
//        return sysDemoMapper.deleteByIds(ids);
//    }
//
//}
