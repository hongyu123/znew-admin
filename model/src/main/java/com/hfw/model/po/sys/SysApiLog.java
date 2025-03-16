package com.hfw.model.po.sys;

import cn.xbatis.db.annotations.Table;
import cn.xbatis.db.annotations.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * 接口日志
 * @author farkle
 * @date 2023-01-13
 */
@Getter @Setter
@Table("sys_api_log")
public class SysApiLog {
    @TableId
    private Long id;

    /** 方法名 **/
    private String method;

    /** 用户id **/
    private Long userId;

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
