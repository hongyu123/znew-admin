package com.hfw.admin.service;

import com.hfw.admin.easyexcel.EasyExcelDataHander;
import com.hfw.common.entity.PageResult;
import com.hfw.model.entity.SysDemo;

import java.util.List;

/**
 * 系统示例表Service
 * @author 
 * @date 2023-01-04
 */
public interface SysDemoService extends EasyExcelDataHander<SysDemo> {
    /**
     * 获取分页数据
     * @return
     */
    PageResult<SysDemo> page(SysDemo sysDemo);
    List<SysDemo> list(SysDemo sysDemo);

    SysDemo detail(Long id);
    void add(SysDemo sysDemo);
    void edit(SysDemo sysDemo);
    void del(Long id);

}