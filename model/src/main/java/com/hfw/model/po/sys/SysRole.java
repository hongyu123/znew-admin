package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Ignore;
import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableField;
import cn.xbatis.db.annotations.TableId;
import com.hfw.model.enums.sys.EnableState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* 系统角色
* @author farkle
* @date 2022-12-14
*/
@Getter @Setter
@Table("sys_role")
public class SysRole {
    @TableId
    private Long id;

    /** 角色名 **/
    private String name;

    /** 角色编码 **/
    private String code;

    /** 排序 **/
    private Integer sort;

    /** 状态 **/
    private EnableState state;

    /** 是否系统内置 **/
    private Integer systemFlag;

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
    private List<SysAuth> authList;

}
