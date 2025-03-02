package com.hfw.model.po;

import cn.xbatis.db.annotations.Table;
import com.hfw.model.entity.BaseEntity;
import lombok.*;

/**
* 系统用户-角色
* @author farkle
* @date 2022-12-14
*/
@Getter @Setter
@Table("sys_user_role")
public class SysUserRole extends BaseEntity {

    /** id **/
    private Long id;

    /** 用户id **/
    private Long userId;

    /** 角色id **/
    private Long roleId;

}