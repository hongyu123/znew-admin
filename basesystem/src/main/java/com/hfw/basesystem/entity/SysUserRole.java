package com.hfw.basesystem.entity;

import com.hfw.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.Table;

/**
* 系统用户-角色
* @author farkle
* @date 2022-12-14
*/
@Getter @Setter @ToString
@Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "sys_user_role")
public class SysUserRole extends BaseEntity {

    /** id **/
    private Long id;

    /** 用户id **/
    private Long userId;

    /** 角色id **/
    private Long roleId;

}