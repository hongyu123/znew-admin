package com.hfw.basesystem.mapper;

import com.hfw.basesystem.dto.SysAuthDTO;
import com.hfw.basesystem.entity.SysAuth;

import java.util.List;

/**
 * 系统权限Mapper
 * @author farkle
 * @date 2022-12-14
 */
public interface SysAuthMapper {
    /**
     * 条件查询list
     * @return
     */
    List<SysAuth> list(SysAuth sysAuth);

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