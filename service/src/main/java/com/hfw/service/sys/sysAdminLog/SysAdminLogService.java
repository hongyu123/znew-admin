package com.hfw.service.sys.sysAdminLog;

import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysAdminLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * admin日志服务
 * @author farkle
 * @date 2025-03-22
 */
@Service
public class SysAdminLogService {
    @Autowired
    private SysAdminLogMapper sysAdminLogMapper;

    public Page<SysAdminLog> page(Page<SysAdminLog> page, SysAdminLog po) {
        return sysAdminLogMapper.page(page, po);
    }

    public int add(SysAdminLog sysAdminLog){
        return sysAdminLogMapper.save(sysAdminLog);
    }

}
