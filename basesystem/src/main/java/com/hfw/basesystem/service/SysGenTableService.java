package com.hfw.basesystem.service;

import com.hfw.common.entity.PageResult;
import com.hfw.basesystem.entity.SysGenTable;

/**
 * 表单生成记录Service
 * @author 
 * @date 2023-01-04
 */
public interface SysGenTableService {
    /**
     * 获取分页数据
     * @return
     */
    PageResult<SysGenTable> page(SysGenTable sysGenTable);

    SysGenTable detail(Long id);
}