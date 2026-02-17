package com.hfw.service.sys.sysLoginLog;

import com.hfw.model.enums.sys.LogoutType;
import com.hfw.model.plugins.ip.OpenIP;
import com.hfw.model.po.sys.SysLoginLog;
import com.hfw.model.utils.StrUtil;
import com.hfw.service.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class SysLoginLogService {
    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;

    public int login(String token, String account, String message, HttpServletRequest request){
        String ip = RequestUtil.getIpAddr(request);
        Map<String, String> browser = RequestUtil.getSystemBrowserInfo(request);
        SysLoginLog log = new SysLoginLog();
        log.setToken(token);
        log.setAccount(account);
        log.setIp(ip);
        log.setLocation("127.0.0.1".equals(ip) ?"" :OpenIP.baidu(ip));
        log.setBrowser(browser.get("browser"));
        log.setOs(browser.get("system"));
        log.setMessage( StrUtil.limitLength(message,255) );
        log.setLoginTime(LocalDateTime.now());
        log.setState(token==null?0:1);
        log.setOnlineFlag(1);
        return sysLoginLogMapper.insert(log);
    }

    public int logout(String token, LogoutType logoutType){
        return sysLoginLogMapper.logout(token, logoutType);
    }

}
