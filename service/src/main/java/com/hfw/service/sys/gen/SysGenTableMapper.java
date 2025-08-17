package com.hfw.service.sys.gen;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysGenTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 表单生成记录Mapper
 * @author farkle
 * @date 2025-03-16
 */
@Mapper
public interface SysGenTableMapper extends MybatisMapper<SysGenTable> {
    /**
     * 分页条件查询
     * @param page 分页参数
     * @param po 实体类参数
     * @return 分页数据
     */
    @Paging
    Page<SysGenTable> page(@Param("page") Page<SysGenTable> page, @Param("po") SysGenTable po);

}
