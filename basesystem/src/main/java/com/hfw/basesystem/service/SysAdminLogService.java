package com.hfw.basesystem.service;


import com.hfw.basesystem.entity.SysAdminLog;
import com.hfw.common.entity.PageResult;

/**
 * admin日志Service
 * @author zyh
 * @date 2022-12-16
 */
public interface SysAdminLogService {
    /**
     * 获取分页数据
     * @return
     */
    PageResult<SysAdminLog> page(SysAdminLog sysAdminLog);

    int save(SysAdminLog sysAdminLog);

    /**
     * 日志记录
     * @param sysAdminLog
     * @return
     */
    int log(SysAdminLog sysAdminLog);
}