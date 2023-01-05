package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.entity.SysAdminLog;
import com.hfw.basesystem.mapper.SysAdminLogMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.SysAdminLogService;
import com.hfw.common.entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * admin日志服务实现
 * @author zyh
 * @date 2022-12-16
 */
@Service
public class SysAdminLogServiceImpl implements SysAdminLogService {

    @Autowired
    private SysAdminLogMapper sysAdminLogMapper;
    @Autowired
    private CommonDao commonDao;

    @Override
    public PageResult<SysAdminLog> page(SysAdminLog sysAdminLog) {
        PageResult<SysAdminLog> page = new PageResult<>(sysAdminLog);
        page.startPage();
        List<SysAdminLog> list = sysAdminLogMapper.list(sysAdminLog);
        page.setList(list);
        return page;
    }

    @Override
    public int save(SysAdminLog sysAdminLog){
        return commonDao.insert(sysAdminLog);
    }

    @Override
    @Async
    public int log(SysAdminLog sysAdminLog){
        return this.save(sysAdminLog);
    }

}