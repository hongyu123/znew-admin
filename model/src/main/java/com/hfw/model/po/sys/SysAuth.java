package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.enums.sys.SysAuthEnum;
import lombok.Getter;
import lombok.Setter;

/**
* 系统权限
* @author farkle
* @date 2022-12-14
*/
@Getter @Setter
@Table("sys_auth")
public class SysAuth {
    @TableId
    private Long id;

    /** 父id **/
    private Long parentId;

    /** 名称 **/
    private String name;

    /** 权限编码 **/
    private String code;
    //包含权限
    private String containsCode;

    /** 前端权限编码 **/
    private String webCode;

    /** 权限类型(菜单,目录,按钮,权限) **/
    private SysAuthEnum authType;
    public String getAuthTypeDesc(){
        return authType==null ?"":authType.getDesc();
    }

    /** 排序 **/
    private Integer sort;

    /** 菜单图标 **/
    private String icon;

    /** 路由地址 **/
    private String path;

    /** 组件路径 **/
    private String component;

    /** 是否外链 **/
    private Integer frameFlag;

    /** 外链地址 **/
    private String frameUrl;

    /** 是否缓存 **/
    private Integer cacheFlag;

    /** 是否显示 **/
    private Integer showFlag;

    /** 状态 **/
    private EnableState state;

    /** 创建账号 **/
    private String creator;

    /** 创建时间 **/
    private java.time.LocalDateTime createTime;

    /** 更新账号 **/
    private String updator;

    /** 更新时间 **/
    private java.time.LocalDateTime updateTime;

    /** 备注 **/
    private String remark;


    public void saveFilter(){
        this.setId(null);
        this.setUpdator(null);
        this.setUpdateTime(null);
    }
    public void updateFilter(){
        this.setCreator(null);
        this.setCreateTime(null);
    }

}
