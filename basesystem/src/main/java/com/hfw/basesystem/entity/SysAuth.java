package com.hfw.basesystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hfw.basesystem.enums.SysAuthEnum;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.BaseEntity;
import com.hfw.common.enums.EnableState;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* 系统权限
* @author farkle
* @date 2022-12-14
*/
@Data
@Accessors(chain = true)
@Table(name = "sys_auth")
public class SysAuth extends BaseEntity {

    /** id **/
    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
    private Long id;

    /** 父id **/
    private Long parentId;

    /** 名称 **/
    @NotBlank(message = "名称不能为空", groups = ValidGroup.Add.class)
    @Length(max = 50,message = "名称最多50字符")
    private String name;

    /** 权限编码 **/
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String code;
    //包含权限
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String containsCode;

    /** 前端权限编码 **/
    private String webCode;

    /** 权限类型(菜单,目录,按钮,权限) **/
    @NotNull(message = "权限类型(目录,菜单,安全,权限)不能为空", groups = ValidGroup.Add.class)
    private SysAuthEnum authType;
    public String getAuthTypeDesc(){
        return authType==null ?"":authType.getDesc();
    }

    /** 排序 **/
    private Integer sort;
    public Integer getSort() {
        return sort;
    }
    public void setSort(Integer sort) {
        this.sort = sort;
    }

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
    @Length(max = 50,message = "创建账号最多50字符")
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