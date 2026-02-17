package com.hfw.model.po.sys;

import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.enums.sys.SysAuthEnum;
import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 系统权限
 * @author farkle
 * @date 2026-02-10
 */
@Getter @Setter
@Table("sys_auth")
public class SysAuth {

    @TableId
    private Long id;

    /** 父id */
    private Long parentId;

    /** 名称(菜单名) */
    private String title;

    /** 权限编码 */
    private String code;

    /** 前端权限编码(路由名/按钮名) */
    private String name;

    /** 权限类型(菜单,目录,按钮,权限) */
    private SysAuthEnum authType;
    public String getAuthTypeDesc(){
        return authType==null ?"":authType.getDesc();
    }

    /** 排序 */
    private Integer sort;

    /** 菜单图标 */
    private String icon;

    /** 路由地址 */
    private String path;

    /** 组件路径 */
    private String component;

    /** 是否外链 */
    private Integer frameFlag;

    /** 外链地址 */
    private String frameUrl;

    /** 是否缓存 */
    private Integer cacheFlag;

    /** 是否显示 */
    private Integer showFlag;

    /** 状态 */
    private EnableState state;

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

    /** 备注 */
    private String remark;


    public enum COLUMN implements Column<SysAuth>{
        id,
        parentId,title,code,name,authType, sort,icon,path,component,frameFlag,
        frameUrl,cacheFlag,showFlag,state,createUser, createTime,updateUser,updateTime,remark
    }

    private List<SysAuth> children;
}
