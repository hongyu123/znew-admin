package com.hfw.service.sys.sysOrganization;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.db.annotations.Paging;
import com.hfw.model.entity.Page;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.po.sys.SysOrganization;
import lombok.extern.java.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织机构Mapper
 * @author farkle
 * @date 2025-03-16
 */
@Mapper
public interface SysOrganizationMapper extends MybatisMapper<SysOrganization> {
    /**
     * 分页条件查询
     * @param page 分页参数
     * @param po 实体类参数
     * @return 分页数据
     */
    @Paging
    Page<SysOrganization> page(@Param("page") Page<SysOrganization> page, @Param("po") SysOrganization po);

    /**
     * 根据机构ID获取机构树列表
     * @param ancestors 机构路径
     * @param state 状态
     * @return 机构树列表
     */
    List<SysOrganization> orgTreeList(@Param("ancestors")String ancestors, @Param("state")EnableState state);

}
