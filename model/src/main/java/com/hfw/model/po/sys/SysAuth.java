package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Ignore;
import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableField;
import cn.xbatis.db.annotations.TableId;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.enums.sys.SysAuthEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    /** 标题 **/
    private String title;

    /** 路由/权限名称 **/
    private String name;

    /** 权限编码 **/
    private String code;

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

    /** 备注 **/
    private String remark;

    /** 创建账号 */
    @TableField(defaultValue = "{CREATE_USER}")
    private String createUser;

    /** 创建时间 */
    @TableField(defaultValue = "{CREATE_TIME}")
    private java.time.LocalDateTime createTime;

    /** 更新账号 */
    @TableField(updateDefaultValue = "{UPDATE_USER}")
    private String updateUser;

    /** 更新时间 */
    @TableField(updateDefaultValue = "{UPDATE_TIME}")
    private java.time.LocalDateTime updateTime;

    @Ignore
    private List<SysAuth> children;

}
