package com.hfw.service.sys.sysAdminLog;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysAdminLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * admin日志Mapper
 * @author farkle
 * @date 2025-03-22
 */
@Mapper
public interface SysAdminLogMapper extends MybatisMapper<SysAdminLog> {
    /**
     * 分页条件查询
     * @param page 分页参数
     * @param po 实体类参数
     * @return 分页数据
     */
    @Paging
    Page<SysAdminLog> page(@Param("page") Page<SysAdminLog> page, @Param("po") SysAdminLog po);

}
