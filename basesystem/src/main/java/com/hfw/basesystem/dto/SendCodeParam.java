package com.hfw.basesystem.dto;

import com.hfw.basesystem.enums.SmsCodeEnum;
import com.hfw.common.util.ValidUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 短信发送参数
 * @author zyh
 * @date 2022-11-26
 */
@Data
public class SendCodeParam {

    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = ValidUtil.phoneReg, message = "手机号码格式错误(11位数字)")
    private String phone;

    @NotNull(message = "短信发送类型不能为空")
    private SmsCodeEnum type;
}
