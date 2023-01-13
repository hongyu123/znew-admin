package com.hfw.basesystem.entity;

import com.hfw.common.enums.EnableState;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import com.hfw.common.entity.BaseEntity;
import com.hfw.basesystem.support.validation.ValidGroup;

/**
* 系统角色
* @author farkle
* @date 2022-12-14
*/
@Data
@Accessors(chain = true)
@Table(name = "sys_role")
public class SysRole extends BaseEntity {

    /** id **/
    @NotNull(message = "id不能为空",groups = {ValidGroup.Update.class, ValidGroup.Del.class})
    private Long id;

    /** 角色名 **/
    @NotBlank(message = "角色名不能为空", groups = ValidGroup.Add.class)
    @Length(max = 50,message = "角色名最多50字符")
    private String name;

    /** 角色编码 **/
    private String code;

    /** 排序 **/
    private Integer sort;

    /** 状态 **/
    @NotNull(message = "状态不能为空", groups = ValidGroup.Add.class)
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

    /** 是否系统内置 **/
    private Integer systemFlag;

}