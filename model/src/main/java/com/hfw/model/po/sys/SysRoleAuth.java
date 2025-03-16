package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import lombok.Getter;
import lombok.Setter;

/**
* 系统角色-权限
* @author farkle
* @date 2022-12-14
*/
@Getter @Setter
@Table("sys_role_auth")
public class SysRoleAuth {
    @TableId
    private Long id;

    /** 角色id **/
    private Long roleId;

    /** 权限id **/
    private Long authId;

}
