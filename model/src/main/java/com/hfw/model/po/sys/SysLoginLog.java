package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hfw.model.enums.sys.LogoutType;
import lombok.Getter;
import lombok.Setter;

/**
* 系统登录日志
* @author farkle
* @date 2022-12-17
*/
@Getter @Setter
@Table("sys_login_log")
public class SysLoginLog {
    @TableId
    private Long id;

    /** 用户账号 **/
    private String account;

    /** 登录IP **/
    private String ip;

    /** 登录地点 **/
    private String location;

    /** 浏览器类型 **/
    private String browser;

    /** 操作系统 **/
    private String os;

    /** 提示消息 **/
    private String message;

    /** 登录时间 **/
    private java.time.LocalDateTime loginTime;

    /** 状态(1成功0失败) **/
    private Integer state;

    /** 在线状态 **/
    private Integer onlineFlag;

    /** 登出类型 **/
    private LogoutType logoutType;
    public String getLogoutTypeDesc(){
        return logoutType==null ?"":logoutType.getDesc();
    }

    /** 登出时间 **/
    private java.time.LocalDateTime logoutTime;

    @JsonIgnore
    private String token;

}
