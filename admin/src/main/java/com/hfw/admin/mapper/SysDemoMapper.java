package com.hfw.admin.mapper;

import com.hfw.model.entity.SysDemo;
import java.util.List;

/**
 * 系统示例表Mapper
 * @author 
 * @date 2023-01-04
 */
public interface SysDemoMapper {
    /**
     * 条件查询list
     * @return
     */
    List<SysDemo> list(SysDemo sysDemo);
}