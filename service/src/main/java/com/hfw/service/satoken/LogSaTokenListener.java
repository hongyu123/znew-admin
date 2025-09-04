package com.hfw.service.satoken;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import com.hfw.model.enums.sys.LogoutType;
import com.hfw.service.sys.sysLoginLog.SysLoginLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogSaTokenListener implements SaTokenListener {
    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;

    /** 每次登录时触发 */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginParameter loginParameter) {
    }

    /** 每次注销时触发 */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        sysLoginLogMapper.logout(tokenValue, LogoutType.Logout);
    }

    /** 每次被踢下线时触发 */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        sysLoginLogMapper.logout(tokenValue, LogoutType.Kickout);
    }

    /** 每次被顶下线时触发 */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
        sysLoginLogMapper.logout(tokenValue, LogoutType.PushedOff);
    }

    /** 每次被封禁时触发 */
    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {
    }

    /** 每次被解封时触发 */
    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {
    }

    /** 每次二级认证时触发 */
    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {
    }

    /** 每次退出二级认证时触发 */
    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {
    }

    /** 每次创建Session时触发 */
    @Override
    public void doCreateSession(String id) {
    }

    /** 每次注销Session时触发 */
    @Override
    public void doLogoutSession(String id) {
    }

    /** 每次Token续期时触发 */
    @Override
    public void doRenewTimeout(String loginType, Object loginId, String tokenValue, long timeout) {
    }

}
