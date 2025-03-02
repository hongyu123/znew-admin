package com.hfw.model.po.app;

import cn.xbatis.db.annotations.Table;
import com.hfw.model.entity.BaseEntity;
import com.hfw.model.validation.ValidGroup;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * app用户扩展信息
 * @author farkle
 * @date 2023-01-09
 */
@Getter @Setter
@Table("app_user_ext")
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