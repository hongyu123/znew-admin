package com.hfw.basesystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hfw.basesystem.enums.LogoutType;
import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
* 系统登录日志
* @author farkle
* @date 2022-12-17
*/
@Data
@Accessors(chain = true)
@Table(name = "sys_login_log")
public class SysLoginLog extends BaseEntity {

    /** 访问ID **/
    @NotNull(message = "id不能为空",groups = {ValidGroup.Update.class, ValidGroup.Del.class})
    private Long id;

    /** 用户账号 **/
    @Length(max = 50,message = "用户账号最多50字符")
    private String account;

    /** 登录IP **/
    @Length(max = 128,message = "登录IP最多128字符")
    private String ip;

    /** 登录地点 **/
    private String location;

    /** 浏览器类型 **/
    private String browser;

    /** 操作系统 **/
    private String os;

    /** 提示消息 **/
    @Length(max = 255,message = "提示消息最多255字符")
    private String message;

    /** 登录时间 **/
    @NotNull(message = "登录时间不能为空", groups = ValidGroup.Add.class)
    private java.time.LocalDateTime loginTime;

    /** 状态(1成功0失败) **/
    private Integer state;

    /** 在线状态 **/
    private Integer onlineFlag;

    /** 登出类型 **/
    private LogoutType logoutType;

    /** 登出时间 **/
    private java.time.LocalDateTime logoutTime;

    @JsonIgnore
    private String token;
}