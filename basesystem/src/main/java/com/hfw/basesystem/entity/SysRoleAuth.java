package com.hfw.basesystem.entity;

import com.hfw.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.Table;

/**
* 系统角色-权限
* @author farkle
* @date 2022-12-14
*/
@Getter @Setter @ToString
@Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "sys_role_auth")
public class SysRoleAuth extends BaseEntity {

    /** id **/
    private Long id;

    /** 角色id **/
    private Long roleId;

    /** 权限id **/
    private Long authId;

}