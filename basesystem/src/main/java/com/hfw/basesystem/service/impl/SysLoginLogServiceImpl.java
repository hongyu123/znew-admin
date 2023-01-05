package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.entity.SysLoginLog;
import com.hfw.basesystem.enums.LogoutType;
import com.hfw.basesystem.mapper.SysLoginLogMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.SysLoginLogService;
import com.hfw.common.entity.PageResult;
import com.hfw.common.util.RequestUtil;
import com.hfw.common.util.StrUtil;
import com.hfw.plugins.ip.OpenIPApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 系统登录日志服务实现
 * @author zyh
 * @date 2022-12-17
 */
@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {

    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;
    @Autowired
    private CommonDao commonDao;
    @Autowired
    private RedisAuth redisAuth;

    @Override
    public PageResult<SysLoginLog> page(SysLoginLog sysLoginLog) {
        PageResult<SysLoginLog> page = new PageResult<>(sysLoginLog);
        page.startPage();
        List<SysLoginLog> list = sysLoginLogMapper.list(sysLoginLog);
        for(SysLoginLog log : list){
            if(log.getOnlineFlag()==1 && !redisAuth.exists(log.getToken())){
                log.setOnlineFlag(0);
                log.setLogoutType(LogoutType.expire);
                commonDao.updateByPk(new SysLoginLog().setId(log.getId()).setOnlineFlag(log.getOnlineFlag()).setLogoutType(log.getLogoutType()));
            }
        }
        page.setList(list);
        return page;
    }

    @Override
    public int login(String token, String account, String message, HttpServletRequest request){
        String ip = RequestUtil.getIpAddr(request);
        Map<String, String> browser = RequestUtil.getSystemBrowserInfo(request);
        SysLoginLog log = new SysLoginLog();
        log.setToken(token);
        log.setAccount(account);
        log.setIp(ip);
        log.setLocation(OpenIPApi.baidu("ip"));
        log.setBrowser(browser.get("browser"));
        log.setOs(browser.get("system"));
        log.setMessage( StrUtil.limitLength(message,255) );
        log.setLoginTime(LocalDateTime.now());
        log.setState(token==null?0:1);
        log.setOnlineFlag(1);
        return commonDao.insert(log);
    }

    @Override
    public int logout(List<String> tokens){
        if(CollectionUtils.isEmpty(tokens)){
            return 0;
        }
        return sysLoginLogMapper.logout(tokens);
    }

    @Override
    public int pushedOff(String pushedOffToken){
        if(!StringUtils.hasText(pushedOffToken)){
            return 0;
        }
        return sysLoginLogMapper.pushedOff(pushedOffToken);
    }

}