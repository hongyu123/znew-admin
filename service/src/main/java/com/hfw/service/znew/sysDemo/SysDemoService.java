package com.hfw.service.znew.sysDemo;

import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.po.znew.SysDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统示例表服务
 * @author farkle
 * @date 2026-02-17
 */
@Service
public class SysDemoService {
    @Autowired
    private SysDemoMapper sysDemoMapper;

    public PageResult<SysDemo> page(Page<SysDemo> page, SysDemo po) {
        page.startPage();
        List<SysDemo> list = sysDemoMapper.list(page, po);
        return PageResult.of(list);
    }

}
