package com.hfw.basesystem.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Table;

import com.hfw.common.entity.BaseEntity;

/**
* 系统角色-权限
* @author farkle
* @date 2022-12-14
*/
@Data
@Accessors(chain = true)
@Table(name = "sys_role_auth")
public class SysRoleAuth extends BaseEntity {

    /** id **/
    private Long id;

    /** 角色id **/
    private Long roleId;

    /** 权限id **/
    private Long authId;

}