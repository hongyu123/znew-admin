package com.hfw.model.po.sys;

import com.hfw.model.mybatis.anno.*;
import com.hfw.model.mybatis.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * admin日志
 * @author farkle
 * @date 2026-02-10
 */
@Getter @Setter
@Table("sys_admin_log")
public class SysAdminLog {

    @TableId
    private Long id;

    /** 标题 */
    private String title;

    /** 方法名 */
    private String method;

    /** 账号 */
    private String account;

    /** 请求url */
    private String requestUrl;

    /** 请求IP */
    private String requestIp;

    /** 请求体 */
    private String requestBody;

    /** 请求头 */
    private String requestHeader;

    /** 响应 */
    private String response;

    /** 状态(1正常,0异常) */
    private Integer state;

    /** 异常信息 */
    private String message;

    /** 耗时 */
    private Long times;

    /** 请求时间 */
    private java.time.LocalDateTime requestTime;


    public enum COLUMN implements Column<SysAdminLog>{
        id,
        title,method,account,requestUrl,requestIp, requestBody,requestHeader,response,state,message,
        times,requestTime
    }
}
