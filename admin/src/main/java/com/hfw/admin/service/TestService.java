package com.hfw.admin.service;

import com.hfw.model.entity.GeneralException;
import com.hfw.model.po.sys.SysDemo;
import com.hfw.service.sys.sysDemo.SysDemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

    @Autowired
    SysDemoMapper sysDemoMapper;

    @Transactional
    public void test(){
        SysDemo sysDemo = new SysDemo();
        sysDemo.setId(5L);
        sysDemo.setAge(0);
        sysDemoMapper.update(sysDemo);
        sysDemo.setAge(1);
        sysDemoMapper.update(sysDemo);
    }

}
