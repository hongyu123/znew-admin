package com.hfw.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 微信登录参数
 * @author zyh
 * @date 2022-11-26
 */
@Data
public class WeixinLoginParam {

    @NotBlank(message = "微信code不能为空")
    private String code;

}
