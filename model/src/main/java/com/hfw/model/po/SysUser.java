package com.hfw.model.po;

import cn.xbatis.db.annotations.Table;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hfw.model.entity.BaseEntity;
import com.hfw.model.enums.EnableState;
import com.hfw.model.enums.Gender;
import com.hfw.model.validation.ValidGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

/**
* 系统用户
* @author farkle
* @date 2022-12-14
*/
@Getter @Setter
@Table("sys_user")
public class SysUser extends BaseEntity {

    /** id **/
    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
    private Long id;

    /** 账号 **/
    @NotBlank(message = "账号不能为空", groups = ValidGroup.Add.class)
    @Length(max = 50,message = "账号最多50字符")
    private String account;

    /** 密码 **/
    @NotBlank(message = "密码不能为空", groups = ValidGroup.Add.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /** 手机号码 **/
    private String phone;

    /** 昵称 **/
    private String nickname;

    /** 头像 **/
    private String avatar;

    /** 邮箱 **/
    private String email;

    /** 性别 **/
    private Gender gender;

    /** 状态 **/
    @NotNull(message = "状态不能为空", groups = ValidGroup.Add.class)
    private EnableState state;

    /** 是否系统内置 **/
    private Integer systemFlag;

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

}