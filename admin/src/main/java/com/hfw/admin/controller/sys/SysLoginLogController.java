package com.hfw.admin.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.enums.sys.LogoutType;
import com.hfw.model.jackson.Result;
import com.hfw.model.mybatis.Where;
import com.hfw.model.mybatis.anno.Sort;
import com.hfw.model.po.sys.SysLoginLog;
import com.hfw.service.component.CommonService;
import com.hfw.service.dto.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

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

    @SaCheckPermission("sysLoginLog:page")
    @GetMapping("/page")
    public PageResult<SysLoginLog> page(Page<SysLoginLog> page, SysLoginLog po) {
        Map<String, String> params = page.getParams();
        Where<SysLoginLog> where = Where.<SysLoginLog>where()
                .eq(StringUtils.hasText(po.getAccount()), SysLoginLog.COLUMN.account, po.getAccount())
                .like(StringUtils.hasText(po.getIp()), SysLoginLog.COLUMN.ip, po.getIp() + "%")
                .like(StringUtils.hasText(po.getLocation()), SysLoginLog.COLUMN.location, "%" + po.getLocation() + "%")
                .eq(po.getState() != null, SysLoginLog.COLUMN.state, po.getState())
                .eq(po.getOnlineFlag() != null, SysLoginLog.COLUMN.onlineFlag, po.getOnlineFlag())
                .ge(StringUtils.hasText(params.get("loginTime_gt")), SysLoginLog.COLUMN.loginTime, params.get("loginTime_gt"))
                .le(StringUtils.hasText(params.get("loginTime_lt")), SysLoginLog.COLUMN.loginTime, params.get("loginTime_lt"))
                .eq(po.getLogoutType() != null, SysLoginLog.COLUMN.logoutType, po.getLogoutType());
        if(!where.orderBy(page)){
            where.orderBy(SysLoginLog.COLUMN.id, Sort.DESC);
        }
        PageResult<SysLoginLog> pageResult = commonService.page(SysLoginLog.class, where, page);
        LocalDateTime ago = LocalDateTime.now().plusHours(-1);
        pageResult.getData().forEach(log->{
            if(log.getOnlineFlag()==1 && log.getLoginTime().isBefore(ago)){
                //登录失效
                if(StpUtil.getLoginIdByToken(log.getToken()) == null){
                    log.setOnlineFlag(0);
                    log.setLogoutType(LogoutType.Expire);
                    SysLoginLog update = new SysLoginLog();
                    update.setId(log.getId());
                    update.setOnlineFlag(log.getOnlineFlag());
                    update.setLogoutType(log.getLogoutType());
                    commonService.updateByPk(update);
                }
            }
        });
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

    @SaCheckPermission("sysLoginLog:kickout")
    @PostMapping("/kickout")
    public Result<Void> kickout(@RequestParam Long id){
        SysLoginLog log = commonService.selectByPk(SysLoginLog.class, id);
        if(log!=null){
            StpUtil.kickoutByTokenValue(log.getToken());
        }
        return Result.success();
    }

}
