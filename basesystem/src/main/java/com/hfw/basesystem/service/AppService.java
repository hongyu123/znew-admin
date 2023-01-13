package com.hfw.basesystem.service;

import com.hfw.basesystem.entity.AppAdvice;
import com.hfw.basesystem.entity.AppArticle;
import com.hfw.basesystem.entity.AppVersion;
import com.hfw.basesystem.dto.SendCodeParam;
import com.hfw.basesystem.enums.DeviceEnum;
import com.hfw.basesystem.enums.SmsCodeEnum;

/**
 * app通用服务
 * @author farkle
 * @date 2022-12-02
 */
public interface AppService {
    /**
     * 验证码发送
     * @param codeParam
     */
    void sendCode(SendCodeParam codeParam);

    /**
     * 验证码校验
     * @param type
     * @param phone
     * @param code
     * @return
     */
    boolean validCode(SmsCodeEnum type, String phone, String code);

    /**
     * 更新手机号码token
     * @param phone
     * @return
     */
    String editPhoneToken(String phone);

    /**
     * 验证更新手机号码token
     * @param phone
     * @param token
     * @return
     */
    boolean validEditPhoneToken(String phone, String token);

    /**
     * app建议反馈
     * @param advice
     * @return
     */
    int advice(AppAdvice advice);

    /**
     * 获取app版本是否有更新
     * @param device
     * @param version
     * @return
     */
    AppVersion appVersion(DeviceEnum device, String version);

    /**
     * 根据id查找文章
     * @param id
     * @return
     */
    AppArticle appArticle(Long id);
}
