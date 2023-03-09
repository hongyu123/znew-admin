package com.hfw.basesystem.entity;

import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * app用户扩展信息
 * @author
 * @date 2023-01-09
 */
@Getter @Setter @ToString
@Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "app_user_ext")
public class AppUserExt extends BaseEntity {

    /** id */
    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
    private Long id;

    /** 用户id */
    @NotNull(message = "用户id不能为空", groups = ValidGroup.Add.class)
    private Long userId;

    /** 微信openid */
    private String openid;

    /** 微信unionid */
    private String unionid;

    /** 苹果登录id */
    private String appleid;


}