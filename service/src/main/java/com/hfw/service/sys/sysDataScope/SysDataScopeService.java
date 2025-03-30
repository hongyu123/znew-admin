package com.hfw.service.sys.sysDataScope;

import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysDataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据权限表服务
 * @author farkle
 * @date 2025-03-30
 */
@Service
public class SysDataScopeService {
    @Autowired
    private SysDataScopeMapper sysDataScopeMapper;

    public Page<SysDataScope> page(Page<SysDataScope> page, SysDataScope po) {
        return sysDataScopeMapper.page(page, po);
    }
    public SysDataScope detail(Long id){
        return sysDataScopeMapper.getById(id);
    }

    public int add(SysDataScope sysDataScope){
        return sysDataScopeMapper.save(sysDataScope);
    }
    public int edit(SysDataScope sysDataScope){
        return sysDataScopeMapper.update(sysDataScope);
    }
    public int del(Long id){
        return sysDataScopeMapper.deleteById(id);
    }
    public int dels(List<Long> ids){
        return sysDataScopeMapper.deleteByIds(ids);
    }

}
