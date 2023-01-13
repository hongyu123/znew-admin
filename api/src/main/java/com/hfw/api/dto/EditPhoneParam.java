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

    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = ValidUtil.phoneReg, message = "手机号码格式错误(11位数字)")
    /**
     * 手机号码
     */
    private String phone;

    @NotBlank(message = "验证码不能为空")
    /**
     * 验证码
     */
    private String code;

    @NotBlank(message = "editToken不能为空")
    private String editToken;

}
