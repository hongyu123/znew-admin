package com.hfw.service.sys.sysLoginLog;

import cn.dev33.satoken.stp.StpUtil;
import com.hfw.model.entity.Page;
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

    public Page<SysLoginLog> page(Page<SysLoginLog> page, SysLoginLog po) {
        Page<SysLoginLog> pageResult = sysLoginLogMapper.page(page, po);
        LocalDateTime ago = LocalDateTime.now().plusHours(-1);
        pageResult.getList().forEach(log->{
            if(log.getOnlineFlag()==1 && log.getLoginTime().isBefore(ago)){
                //登录失效
                if(StpUtil.getLoginIdByToken(log.getToken()) == null){
                    log.setOnlineFlag(0);
                    log.setLogoutType(LogoutType.Expire);
                    SysLoginLog update = new SysLoginLog();
                    update.setId(log.getId());
                    update.setOnlineFlag(log.getOnlineFlag());
                    update.setLogoutType(log.getLogoutType());
                    sysLoginLogMapper.update(update);
                }
            }
        });
        return pageResult;
    }

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
        return sysLoginLogMapper.save(log);
    }

}
