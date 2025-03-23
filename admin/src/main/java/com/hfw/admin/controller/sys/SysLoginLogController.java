package com.hfw.admin.controller.sys;

import cn.dev33.satoken.stp.StpUtil;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.enums.sys.SortByWay;
import com.hfw.model.jackson.Result;
import com.hfw.model.mapper.CommonMapper;
import com.hfw.model.po.sys.SysLoginLog;
import com.hfw.service.sys.sysLoginLog.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统登录日志Controller
 * @author  farkle
 * @date 2025-03-22
 */
@RestController
@RequestMapping("/sysLoginLog")
public class SysLoginLogController {
    @Autowired
    private SysLoginLogService sysLoginLogService;
    @Autowired
    private CommonMapper commonMapper;

    @GetMapping("/page")
    public PageResult<SysLoginLog> page(Page<SysLoginLog> page, SysLoginLog po) {
        page.sort(SortByWay.desc,"id");
        return PageResult.of(sysLoginLogService.page(page, po));
    }

    @PostMapping("/kickout")
    public Result<Void> kickout(@RequestParam Long id){
        SysLoginLog log = commonMapper.getById(SysLoginLog.class, id);
        if(log!=null){
            StpUtil.kickoutByTokenValue(log.getToken());
        }
        return Result.success();
    }

}
