package com.hfw.basesystem.dto;

import com.hfw.basesystem.entity.SysAuth;
import com.hfw.basesystem.entity.SysRole;
import lombok.Data;

import java.util.List;

/**
 * 系统角色DTO
 */
@Data
public class SysRoleDTO extends SysRole {

    private List<SysAuth> authList;

    /*************************查询用*****************************/
    private String name_like;
    private String code_like;

    /*************************显示用*****************************/


    public SysRole toEntity(){
        SysRole sysRole = new SysRole();
        sysRole.setId(this.getId());
        sysRole.setName(this.getName());
        sysRole.setCode(this.getCode());
        sysRole.setSort(this.getSort());
        sysRole.setState(this.getState());
        sysRole.setCreator(this.getCreator());
        sysRole.setCreateTime(this.getCreateTime());
        sysRole.setUpdator(this.getUpdator());
        sysRole.setUpdateTime(this.getUpdateTime());
        sysRole.setRemark(this.getRemark());
        sysRole.setSystemFlag(this.getSystemFlag());
        return sysRole;
    }
    public SysRoleDTO saveFilter(){
        this.setId(null);
        this.setUpdator(null);
        this.setUpdateTime(null);
        return this;
    }
    public SysRoleDTO updateFilter(){
        this.setCreator(null);
        this.setCreateTime(null);
        return this;
    }

    public static SysRoleDTO of(SysRole sysRole){
        SysRoleDTO dto = new SysRoleDTO();
        dto.setId(sysRole.getId());
        dto.setName(sysRole.getName());
        dto.setCode(sysRole.getCode());
        dto.setSort(sysRole.getSort());
        dto.setState(sysRole.getState());
        dto.setCreator(sysRole.getCreator());
        dto.setCreateTime(sysRole.getCreateTime());
        dto.setUpdator(sysRole.getUpdator());
        dto.setUpdateTime(sysRole.getUpdateTime());
        dto.setRemark(sysRole.getRemark());
        return dto;
    }

}