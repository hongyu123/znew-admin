package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.enums.sys.Gender;
import lombok.Getter;
import lombok.Setter;

/**
* 系统用户
* @author farkle
* @date 2022-12-14
*/
@Getter @Setter
@Table("sys_user")
public class SysUser {

    @TableId
    private Long id;

    /** 账号 **/
    private String account;

    /** 密码 **/
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
