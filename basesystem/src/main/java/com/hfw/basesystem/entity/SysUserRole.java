package com.hfw.basesystem.entity;

import com.hfw.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
* 系统用户-角色
* @author zyh
* @date 2022-12-14
*/
@Data
@Accessors(chain = true)
@Table(name = "sys_user_role")
public class SysUserRole extends BaseEntity {

    /** id **/
    private Long id;

    /** 用户id **/
    private Long userId;

    /** 角色id **/
    private Long roleId;

}