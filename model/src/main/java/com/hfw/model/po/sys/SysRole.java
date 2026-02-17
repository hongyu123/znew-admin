package com.hfw.model.po.sys;

import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 系统角色
 * @author farkle
 * @date 2026-02-10
 */
@Getter @Setter
@Table("sys_role")
public class SysRole {

    @TableId
    private Long id;

    /** 角色名 */
    private String name;

    /** 角色编码 */
    private String code;

    /** 排序 */
    private Integer sort;

    /** 状态 */
    private EnableState state;

    /** 是否系统内置 */
    private Integer systemFlag;

    /** 备注 */
    private String remark;

    /** 创建账号 */
    @TableField(insertValue = "$CURRENT_USER")
    private String createUser;

    /** 创建时间 */
    @TableField(insertValue = "$NOW")
    private java.time.LocalDateTime createTime;

    /** 更新账号 */
    @TableField(updateValue = "$CURRENT_USER")
    private String updateUser;

    /** 更新时间 */
    @TableField(updateValue = "$NOW")
    private java.time.LocalDateTime updateTime;


    public enum COLUMN implements Column<SysRole>{
        id,
        name,code,sort,state,systemFlag, remark,createUser,createTime,updateUser,updateTime
    }

    private List<SysAuth> authList;
}
