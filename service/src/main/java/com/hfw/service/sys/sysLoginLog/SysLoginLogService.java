package com.hfw.service.sys.sysLoginLog;

import com.hfw.model.enums.sys.LogoutType;
import com.hfw.model.mybatis.Where;
import com.hfw.model.mybatis.anno.Sort;
import com.hfw.model.plugins.ip.OpenIP;
import com.hfw.model.po.sys.SysLoginLog;
import com.hfw.model.utils.StrUtil;
import com.hfw.service.auth.Token;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SysLoginLogService {
    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;

    public int loginError(String account, String message, HttpServletRequest request){
        String ip = RequestUtil.getIpAddr(request);
        String userAgent = StrUtil.limitLength(request.getHeader("User-Agent"), 250);
        SysLoginLog log = new SysLoginLog();
        log.setAccount(account);
        log.setIp(ip);
        log.setLocation("127.0.0.1".equals(ip) ?"" :OpenIP.baidu(ip));
        log.setUserAgent(userAgent);
        log.setMessage( StrUtil.limitLength(message,255) );
        log.setLoginTime(LocalDateTime.now());
        log.setState(0);
        log.setOnlineFlag(0);
        log.setToken("");
        return sysLoginLogMapper.insert(log);
    }
    public int login(LoginUser loginUser, Token token, HttpServletRequest request){
        String ip = RequestUtil.getIpAddr(request);
        String userAgent = StrUtil.limitLength(request.getHeader("User-Agent"), 250);
        SysLoginLog log = new SysLoginLog();
        log.setUserId(loginUser.getId());
        log.setAccount(loginUser.getAccount());
        log.setIp(ip);
        log.setLocation("127.0.0.1".equals(ip) ?"" :OpenIP.baidu(ip));
        log.setUserAgent(userAgent);
        log.setMessage("登录成功");
        log.setLoginTime(LocalDateTime.now());
        log.setState(1);
        log.setOnlineFlag(1);
        log.setToken(token.uuid());
        log.setExpireTime(token.expireTime());
        return sysLoginLogMapper.insert(log);
    }

    public List<SysLoginLog> checkOnline(Long userId){
        sysLoginLogMapper.expire(userId);
        return sysLoginLogMapper.selectList(Where.<SysLoginLog>where().eq(SysLoginLog.COLUMN.userId, userId).eq(SysLoginLog.COLUMN.onlineFlag, 1).orderBy(SysLoginLog.COLUMN.id, Sort.ASC));
    }
    public int logout(Long id, LogoutType logoutType){
        return sysLoginLogMapper.logout(id, logoutType);
    }

}
