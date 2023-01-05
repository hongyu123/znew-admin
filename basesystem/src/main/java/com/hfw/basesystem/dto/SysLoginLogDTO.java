package com.hfw.basesystem.dto;

import com.hfw.basesystem.entity.SysLoginLog;
import lombok.Data;

import java.time.LocalDate;

/**
 * 系统登录日志DTO
 */
@Data
public class SysLoginLogDTO extends SysLoginLog {

    /*************************查询用*****************************/
    private String account_like;
    private String ip_like;
    private String location_like;
    private LocalDate loginTime_gt;
    private LocalDate loginTime_lt;

    /*************************显示用*****************************/


    public SysLoginLog toEntity(){
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setId(this.getId());
        sysLoginLog.setAccount(this.getAccount());
        sysLoginLog.setIp(this.getIp());
        sysLoginLog.setLocation(this.getLocation());
        sysLoginLog.setBrowser(this.getBrowser());
        sysLoginLog.setOs(this.getOs());
        sysLoginLog.setMessage(this.getMessage());
        sysLoginLog.setLoginTime(this.getLoginTime());
        sysLoginLog.setState(this.getState());
        sysLoginLog.setOnlineFlag(this.getOnlineFlag());
        sysLoginLog.setLogoutType(this.getLogoutType());
        sysLoginLog.setLogoutTime(this.getLogoutTime());
        return sysLoginLog;
    }

}