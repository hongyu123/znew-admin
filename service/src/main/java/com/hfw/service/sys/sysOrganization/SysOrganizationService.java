package com.hfw.service.sys.sysOrganization;

import com.hfw.model.entity.Page;
import com.hfw.model.po.sys.SysOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组织机构服务
 * @author farkle
 * @date 2025-03-16
 */
@Service
public class SysOrganizationService {
    @Autowired
    private SysOrganizationMapper sysOrganizationMapper;

    public Page<SysOrganization> page(Page<SysOrganization> page, SysOrganization po) {
        return sysOrganizationMapper.page(page, po);
    }
    public SysOrganization detail(Long id){
        return sysOrganizationMapper.getById(id);
    }

    public int add(SysOrganization sysOrganization){
        return sysOrganizationMapper.save(sysOrganization);
    }
    public int edit(SysOrganization sysOrganization){
        return sysOrganizationMapper.update(sysOrganization);
    }
    public int del(Long id){
        return sysOrganizationMapper.deleteById(id);
    }
    public int dels(List<Long> ids){
        return sysOrganizationMapper.deleteByIds(ids);
    }

}
