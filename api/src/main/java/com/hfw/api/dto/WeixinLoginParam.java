package com.hfw.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 微信登录参数
 * @author farkle
 * @date 2022-11-26
 */
@Data
public class WeixinLoginParam {

    /** 微信code */
    @NotBlank(message = "微信code不能为空")
    private String code;

}
