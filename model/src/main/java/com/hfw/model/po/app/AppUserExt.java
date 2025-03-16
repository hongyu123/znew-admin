package com.hfw.model.po.app;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * app用户扩展信息
 * @author farkle
 * @date 2023-01-09
 */
@Getter @Setter
@Table("app_user_ext")
public class AppUserExt {
    @TableId
    private Long id;

    /** 用户id */
    private Long userId;

    /** 微信openid */
    private String openid;

    /** 微信unionid */
    private String unionid;

    /** 苹果登录id */
    private String appleid;

}
