package com.hfw.basesystem.entity;

import com.hfw.basesystem.support.validation.ValidGroup;
import com.hfw.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 接口日志
 * @author farkle
 * @date 2023-01-13
 */
@Data
@Accessors(chain = true)
@Table(name = "sys_api_log")
public class SysApiLog extends BaseEntity {

    /** id **/
    @NotNull(message = "id不能为空",groups = ValidGroup.Update.class)
    private Long id;

    /** 方法名 **/
    @NotBlank(message = "方法名不能为空", groups = ValidGroup.Add.class)
    @Length(max = 255,message = "方法名最多255字符")
    private String method;

    /** 用户id **/
    private Long userId;

    /** 请求url **/
    @NotBlank(message = "请求url不能为空", groups = ValidGroup.Add.class)
    @Length(max = 255,message = "请求url最多255字符")
    private String requestUrl;

    /** request_ip **/
    @NotBlank(message = "request_ip不能为空", groups = ValidGroup.Add.class)
    @Length(max = 128,message = "request_ip最多128字符")
    private String requestIp;

    /** 请求体 **/
    @NotBlank(message = "请求体不能为空", groups = ValidGroup.Add.class)
    @Length(max = 2000,message = "请求体最多2,000字符")
    private String requestBody;

    /** 请求头 **/
    private String requestHeader;

    /** 响应 **/
    @NotBlank(message = "响应不能为空", groups = ValidGroup.Add.class)
    @Length(max = 2000,message = "响应最多2,000字符")
    private String response;

    /** 异常状态 **/
    private Integer state;

    /** 异常信息 **/
    private String message;

    /** 耗时 **/
    @NotNull(message = "耗时不能为空", groups = ValidGroup.Add.class)
    private Long times;

    /** 请求时间 **/
    private java.time.LocalDateTime requestTime;

}
