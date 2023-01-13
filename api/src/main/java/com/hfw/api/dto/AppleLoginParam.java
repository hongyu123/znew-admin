package com.hfw.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 苹果登录参数
 * @author farkle
 * @date 2022-11-26
 */
@Data
public class AppleLoginParam {

    @NotBlank(message = "token不能为空")
    private String token;

}
