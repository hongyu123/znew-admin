package com.hfw.model.po.sys;

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统角色-权限
 * @author farkle
 * @date 2026-02-10
 */
@Getter @Setter
@Table("sys_role_auth")
public class SysRoleAuth {

    @TableId
    private Long id;

    /** 角色id */
    private Long roleId;

    /** 权限id */
    private Long authId;


    public enum COLUMN implements Column<SysRoleAuth>{
        id,
        roleId,authId
    }
}
