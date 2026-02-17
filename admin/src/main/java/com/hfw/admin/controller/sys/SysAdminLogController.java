package com.hfw.admin.controller.sys;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.mybatis.Where;
import com.hfw.model.mybatis.anno.Sort;
import com.hfw.model.po.sys.SysAdminLog;
import com.hfw.service.component.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * admin日志Controller
 * @author  farkle
 * @date 2025-03-22
 */
@RestController
@RequestMapping("/sysAdminLog")
public class SysAdminLogController {
    @Autowired
    private CommonService commonService;

    @SaCheckPermission("sysAdminLog:page")
    @GetMapping("/page")
    public PageResult<SysAdminLog> page(Page<SysAdminLog> page, SysAdminLog po) {
        Where<SysAdminLog> where = Where.<SysAdminLog>where()
                .like(StringUtils.hasText(po.getTitle()), SysAdminLog.COLUMN.title, "%" + po.getTitle() + "%")
                .eq(StringUtils.hasText(po.getAccount()), SysAdminLog.COLUMN.account, po.getAccount())
                .eq(StringUtils.hasText(po.getRequestIp()), SysAdminLog.COLUMN.requestIp, po.getRequestIp())
                .ge(po.getTimes()!=null, SysAdminLog.COLUMN.times, po.getTimes())
                .eq(po.getState()!=null, SysAdminLog.COLUMN.state, po.getState());
        //添加默认排序
        if(!where.orderBy(page)){
            where.orderBy(SysAdminLog.COLUMN.id, Sort.DESC);
        }
        return commonService.page(SysAdminLog.class, where, page);
    }

}
