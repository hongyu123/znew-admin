package com.hfw.service.sys.sysAdminLog;

import com.hfw.model.po.sys.SysAdminLog;
import com.hfw.service.component.CommonMapper;
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
    private CommonMapper commonMapper;

    public int add(SysAdminLog sysAdminLog){
        return commonMapper.insert(sysAdminLog);
    }

}
