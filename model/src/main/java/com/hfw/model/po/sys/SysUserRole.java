package com.hfw.model.po.sys;

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 系统用户-角色
 * @author farkle
 * @date 2026-02-10
 */
@Getter @Setter
@Table("sys_user_role")
public class SysUserRole {

    @TableId
    private Long id;

    /** 用户id */
    private Long userId;

    /** 角色id */
    private Long roleId;


    public enum COLUMN implements Column<SysUserRole>{
        id,
        userId,roleId
    }

    private List<Long> userIds;
}
