package com.hfw.basesystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hfw.basesystem.support.validation.Pattern;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.BaseEntity;
import com.hfw.common.enums.EnableState;
import com.hfw.common.enums.Gender;
import com.hfw.common.util.ValidUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* 系统用户
* @author farkle
* @date 2022-12-14
*/
@Data
@Accessors(chain = true)
@Table(name = "sys_user")
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
    @Pattern(regexp = ValidUtil.passwordReg, message = "密码格式错误!")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /** 手机号码 **/
    @Pattern(regexp = ValidUtil.phoneReg, message = "手机号码格式错误!")
    private String phone;

    /** 昵称 **/
    private String nickname;

    /** 头像 **/
    private String avatar;

    /** 邮箱 **/
    @Pattern(regexp = ValidUtil.emailReg, message = "邮箱格式错误!")
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