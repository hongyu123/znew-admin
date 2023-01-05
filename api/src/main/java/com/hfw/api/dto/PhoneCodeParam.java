package com.hfw.api.dto;

import com.hfw.common.util.ValidUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 手机验证码参数
 * @author zyh
 * @date 2022-11-25
 */
@Data
public class PhoneCodeParam {

    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = ValidUtil.phoneReg, message = "手机号码格式错误(11位数字)")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    private String code;
}
