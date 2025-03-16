package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import lombok.Getter;
import lombok.Setter;

/**
* 系统用户-角色
* @author farkle
* @date 2022-12-14
*/
@Getter @Setter
@Table("sys_user_role")
public class SysUserRole {
    @TableId
    private Long id;

    /** 用户id **/
    private Long userId;

    /** 角色id **/
    private Long roleId;

}
