package com.hfw.model.po.app;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.enums.sys.Gender;
import lombok.Getter;
import lombok.Setter;

/**
 * app用户
 * @author farkle
 * @date 2023-01-09
 */
@Getter @Setter
@Table("app_user")
public class AppUser {
    @TableId
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
