package com.hfw.service.sys.sysOrganization;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysOrganization;
import org.apache.ibatis.annotations.Param;

/**
 * 组织机构Mapper
 * @author farkle
 * @date 2025-03-16
 */
public interface SysOrganizationMapper extends MybatisMapper<SysOrganization> {
    /**
     * 分页条件查询
     * @param page 分页参数
     * @param po 实体类参数
     * @return 分页数据
     */
    @Paging
    Page<SysOrganization> page(@Param("page") Page<SysOrganization> page, @Param("po") SysOrganization po);

}
