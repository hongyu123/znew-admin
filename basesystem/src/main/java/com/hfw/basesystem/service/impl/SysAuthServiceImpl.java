package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.dto.SysAuthDTO;
import com.hfw.basesystem.entity.SysAuth;
import com.hfw.basesystem.mapper.SysAuthMapper;
import com.hfw.basesystem.service.SysAuthService;
import com.hfw.common.entity.PageResult;
import com.hfw.common.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 系统权限服务实现
 * @author zyh
 * @date 2022-12-14
 */
@Service
public class SysAuthServiceImpl implements SysAuthService {

    @Autowired
    private SysAuthMapper sysAuthMapper;

    @Override
    public PageResult<SysAuth> page(SysAuth sysAuth) {
        PageResult<SysAuth> page = new PageResult<>(sysAuth);
        page.startPage();
        List<SysAuth> list = sysAuthMapper.list(sysAuth);
        page.setList(list);
        return page;
    }

    @Override
    public List<SysAuthDTO> children(Long parentId){
        return sysAuthMapper.children(parentId);
    }

    @Override
    public List<SysAuthDTO> treeList(SysAuth sysAuth){
        List<SysAuthDTO> list = sysAuthMapper.treeList(sysAuth);
        return ListUtil.listTotree(list);
    }

}