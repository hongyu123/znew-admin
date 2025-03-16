package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableField;
import cn.xbatis.db.annotations.TableId;
import com.hfw.model.enums.sys.EnableState;
import lombok.Getter;
import lombok.Setter;

/**
* 组织机构
* @author farkle
* @date 2025-03-16
*/
@Getter @Setter
@Table("sys_organization")
public class SysOrganization {

    @TableId
    private Long id;

    /** 父部门id */
    private Long pid;

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

}
