package com.hfw.service.sys.sysDemo;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysDemo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统示例表Mapper
 * @author farkle
 * @date 2023-01-04
 */
@Mapper
public interface SysDemoMapper extends MybatisMapper<SysDemo> {

    /**
     * 分页条件查询
     * @param page 分页参数
     * @param po 实体类参数
     * @return 分页数据
     */
    @Paging
    Page<SysDemo> page(@Param("page") Page<SysDemo> page, @Param("po") SysDemo po);

}