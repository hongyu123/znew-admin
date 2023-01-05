package com.hfw.basesystem.mapper;

import com.hfw.basesystem.entity.SysGenTable;

import java.util.List;

/**
 * 表单生成记录Mapper
 * @author 
 * @date 2023-01-04
 */
public interface SysGenTableMapper {
    /**
     * 条件查询list
     * @return
     */
    List<SysGenTable> list(SysGenTable sysGenTable);
}