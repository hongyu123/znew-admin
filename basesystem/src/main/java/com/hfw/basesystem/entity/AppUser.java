package com.hfw.basesystem.entity;

import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.BaseEntity;
import com.hfw.common.enums.EnableState;
import com.hfw.common.enums.Gender;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * app用户
 * @author
 * @date 2023-01-09
 */
@Data
@Accessors(chain = true)
@Table(name = "app_user")
public class AppUser extends BaseEntity {

    /** id */
    @NotNull(message = "id不能为空",groups = {ValidGroup.Update.class, ValidGroup.Del.class})
    private Long id;

    /** 昵称 */
    private String nickname;

    /** 头像 */
    private String avatar;

    /** 手机号码 */
    private String phone;

    /** 性别 */
    private Gender gender;

    /** 生日 */
    private java.time.LocalDate birth;

    /** 地址 */
    private String address;

    /** 积分 */
    private Integer integral;

    /** 余额 */
    private Integer balance;

    /** 角色 */
    private Integer role;

    /** 启用状态 */
    private EnableState enableState;

    /** 备注 */
    private String comment;


}