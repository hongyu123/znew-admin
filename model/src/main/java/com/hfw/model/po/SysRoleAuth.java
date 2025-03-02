package com.hfw.model.po;

import cn.xbatis.db.annotations.Table;
import com.hfw.model.entity.BaseEntity;
import lombok.*;

/**
* 系统角色-权限
* @author farkle
* @date 2022-12-14
*/
@Getter @Setter
@Table("sys_role_auth")
public class SysRoleAuth extends BaseEntity {

    /** id **/
    private Long id;

    /** 角色id **/
    private Long roleId;

    /** 权限id **/
    private Long authId;

}