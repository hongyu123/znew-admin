package com.hfw.api.dto;

import com.hfw.common.util.ValidUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 修改手机号码参数
 * @author farkle
 * @date 2022-11-30
 */
@Data
public class EditPhoneParam {

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = ValidUtil.phoneReg, message = "手机号码格式错误(11位数字)")
    private String phone;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 原手机验证码
     */
    @NotBlank(message = "原手机验证码不能为空")
    private String oldCode;
    //@NotBlank(message = "editToken不能为空")
    //private String editToken;

}
