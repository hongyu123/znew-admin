package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.entity.SysAdminLog;
import com.hfw.basesystem.mapper.SysAdminLogMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.SysAdminLogService;
import com.hfw.common.entity.PageResult;
import javax.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * admin日志服务实现
 * @author farkle
 * @date 2022-12-16
 */
@Service("sysAdminLogService")
public class SysAdminLogServiceImpl implements SysAdminLogService {

    @Resource
    private SysAdminLogMapper sysAdminLogMapper;
    @Resource
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
    public void log(SysAdminLog sysAdminLog){
         this.save(sysAdminLog);
    }

}