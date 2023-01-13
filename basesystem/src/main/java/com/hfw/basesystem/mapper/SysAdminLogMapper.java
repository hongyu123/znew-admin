package com.hfw.basesystem.mapper;

import com.hfw.basesystem.entity.SysAdminLog;

import java.util.List;

/**
 * admin日志Mapper
 * @author farkle
 * @date 2022-12-16
 */
public interface SysAdminLogMapper {
    /**
     * 条件查询list
     * @return
     */
    List<SysAdminLog> list(SysAdminLog sysAdminLog);
}