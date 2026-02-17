package com.hfw.service.sys.sysOrganization;

import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.mybatis.BaseMapper;
import com.hfw.model.po.sys.SysOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 组织机构Mapper
 * @author farkle
 * @date 2025-03-16
 */
@Mapper
public interface SysOrganizationMapper extends BaseMapper<SysOrganization> {

    /**
     * 根据机构ID获取机构树列表
     * @param ancestors 机构路径
     * @param state 状态
     * @return 机构树列表
     */
    List<SysOrganization> orgTreeList(@Param("ancestors")String ancestors, @Param("state") EnableState state);

}
