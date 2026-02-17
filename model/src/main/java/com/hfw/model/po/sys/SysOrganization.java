package com.hfw.model.po.sys;

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
import com.hfw.model.enums.sys.EnableState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 组织机构
 * @author farkle
 * @date 2026-02-10
 */
@Getter @Setter
@Table("sys_organization")
public class SysOrganization {

    @TableId
    private Long id;

    /** 父id */
    private Long pid;

    /** 祖先路径 */
    private String ancestors;

    /** 类型(1组织机构,2部门) */
    private Integer type;

    /** 部门名称 */
    private String name;

    /** 显示顺序 */
    private Integer sort;

    /** 联系人 */
    private String linkName;

    /** 联系电话 */
    private String linkPhone;

    /** 联系邮箱 */
    private String linkEmail;

    /** 状态 */
    private EnableState state;
    public String getStateDesc(){
        return state==null ?"":state.getDesc();
    }

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


    public enum COLUMN implements Column<SysOrganization>{
        id,
        pid,ancestors,type,name,sort, linkName,linkPhone,linkEmail,state,createUser,
        createTime,updateUser,updateTime
    }

    private List<SysOrganization> children;
}
