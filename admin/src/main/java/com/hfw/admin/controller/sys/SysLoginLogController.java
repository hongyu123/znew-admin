package com.hfw.admin.controller.sys;

import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.enums.sys.LogoutType;
import com.hfw.model.jackson.Result;
import com.hfw.model.mybatis.Where;
import com.hfw.model.mybatis.anno.Sort;
import com.hfw.model.po.sys.SysLoginLog;
import com.hfw.service.auth.AuthService;
import com.hfw.service.component.CommonService;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.sys.sysLoginLog.SysLoginLogService;
import com.hfw.service.sys.sysUser.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 系统登录日志Controller
 * @author  farkle
 * @date 2025-03-22
 */
@RestController
@RequestMapping("/sysLoginLog")
public class SysLoginLogController {
    @Autowired
    private CommonService commonService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysLoginLogService sysLoginLogService;
    @Autowired
    private AuthService authService;

    @GetMapping("/page")
    public PageResult<SysLoginLog> page(Page<SysLoginLog> page, SysLoginLog po) {
        page.paramToDateTime("loginTime_gt");
        page.paramToDateTime("loginTime_lt");
        Where<SysLoginLog> where = Where.<SysLoginLog>where()
                .eq(StringUtils.hasText(po.getAccount()), SysLoginLog.COLUMN.account, po.getAccount())
                .like(StringUtils.hasText(po.getIp()), SysLoginLog.COLUMN.ip, po.getIp() + "%")
                .like(StringUtils.hasText(po.getLocation()), SysLoginLog.COLUMN.location, "%" + po.getLocation() + "%")
                .eq(po.getState() != null, SysLoginLog.COLUMN.state, po.getState())
                .eq(po.getOnlineFlag() != null, SysLoginLog.COLUMN.onlineFlag, po.getOnlineFlag())
                .ge(page.get("loginTime_gt")!=null, SysLoginLog.COLUMN.loginTime, page.get("loginTime_gt"))
                .le(page.get("loginTime_lt")!=null, SysLoginLog.COLUMN.loginTime, page.get("loginTime_lt"))
                .eq(po.getLogoutType() != null, SysLoginLog.COLUMN.logoutType, po.getLogoutType());
        if(!where.orderBy(page, true)){
            where.orderBy(SysLoginLog.COLUMN.id, Sort.DESC);
        }
        PageResult<SysLoginLog> pageResult = commonService.page(SysLoginLog.class, where, page);
        LocalDateTime ago = LocalDateTime.now().plusHours(-1);
        if(pageResult.getData()!=null){
            pageResult.getData().forEach(log->{
                if(log.getOnlineFlag()==1 && log.getLoginTime().isBefore(ago) && !authService.exists(log.getToken())){
                    //登录失效
                    sysLoginLogService.logout(log.getId(), LogoutType.Expire);
                }
            });
        }
        return pageResult;
    }

    @GetMapping("/userLoginLog")
    public PageResult<SysLoginLog> page(Page<SysLoginLog> page) {
        Where<SysLoginLog> where = Where.<SysLoginLog>where()
                .eq(SysLoginLog.COLUMN.account, LoginUser.getLoginUser().getAccount());
        if(!where.orderBy(page)){
            where.orderBy(SysLoginLog.COLUMN.id, Sort.DESC);
        }
        return commonService.page(SysLoginLog.class, where, page);
    }

    @PostMapping("/kickout")
    public Result<Void> kickout(@RequestParam Long id){
        SysLoginLog log = commonService.selectByPk(SysLoginLog.class, id);
        if(log!=null){
            sysUserService.logout(log, LogoutType.Kickout);
        }
        return Result.success();
    }

}
