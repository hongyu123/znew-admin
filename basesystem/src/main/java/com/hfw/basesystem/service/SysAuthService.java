package com.hfw.basesystem.service;

import com.hfw.basesystem.dto.SysAuthDTO;
import com.hfw.basesystem.entity.SysAuth;
import com.hfw.common.entity.PageResult;

import java.util.List;

/**
 * 系统权限Service
 * @author zyh
 * @date 2022-12-14
 */
public interface SysAuthService {
    /**
     * 获取分页数据
     * @return
     */
    PageResult<SysAuth> page(SysAuth sysAuth);

    /**
     * 查询子节点
     * @return
     */
    List<SysAuthDTO> children(Long parentId);

    /**
     * 查询树形结构数据
     * @param sysAuth
     * @return
     */
    List<SysAuthDTO> treeList(SysAuth sysAuth);
}