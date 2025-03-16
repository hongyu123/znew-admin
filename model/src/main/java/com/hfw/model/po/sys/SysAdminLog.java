package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import lombok.Getter;
import lombok.Setter;

/**
* admin日志
* @author farkle
* @date 2022-12-16
*/
@Getter @Setter
@Table("sys_admin_log")
public class SysAdminLog {
    @TableId
    private Long id;

    /** 标题 **/
    private String title;

    /** 方法名 **/
    private String method;

    /** 管理员账号 **/
    private String account;

    /** 请求url **/
    private String requestUrl;

    /** request_ip **/
    private String requestIp;

    /** 请求体 **/
    private String requestBody;

    /** 请求头 **/
    private String requestHeader;

    /** 响应 **/
    private String response;

    /** 异常状态 **/
    private Integer state;

    /** 异常信息 **/
    private String message;

    /** 耗时 **/
    private Long times;

    /** 请求时间 **/
    private java.time.LocalDateTime requestTime;

}
