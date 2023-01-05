package com.hfw.basesystem.dto;

import com.hfw.basesystem.entity.SysAdminLog;
import lombok.Data;

/**
 * admin日志DTO
 */
@Data
public class SysAdminLogDTO extends SysAdminLog {

    /*************************查询用*****************************/
    private String title_like;
    private String account_like;
    private String requestIp_like;
    private Integer times_gt;

    /*************************显示用*****************************/


    public SysAdminLog toEntity(){
        SysAdminLog sysAdminLog = new SysAdminLog();
        sysAdminLog.setId(this.getId());
        sysAdminLog.setTitle(this.getTitle());
        sysAdminLog.setMethod(this.getMethod());
        sysAdminLog.setAccount(this.getAccount());
        sysAdminLog.setRequestUrl(this.getRequestUrl());
        sysAdminLog.setRequestIp(this.getRequestIp());
        sysAdminLog.setRequestBody(this.getRequestBody());
        sysAdminLog.setRequestHeader(this.getRequestHeader());
        sysAdminLog.setResponse(this.getResponse());
        sysAdminLog.setState(this.getState());
        sysAdminLog.setMessage(this.getMessage());
        sysAdminLog.setTimes(this.getTimes());
        sysAdminLog.setRequestTime(this.getRequestTime());
        return sysAdminLog;
    }

}