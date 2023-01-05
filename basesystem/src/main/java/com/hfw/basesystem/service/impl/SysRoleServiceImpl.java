package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.dto.SysRoleDTO;
import com.hfw.basesystem.dto.SysUserRoleDTO;
import com.hfw.basesystem.entity.*;
import com.hfw.basesystem.mapper.SysRoleMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.SysRoleService;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色服务实现
 * @author zyh
 * @date 2022-12-14
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private CommonDao commonDao;

    @Override
    public PageResult<SysRole> page(SysRole sysRole) {
        PageResult<SysRole> page = new PageResult<>(sysRole);
        page.startPage();
        List<SysRole> list = sysRoleMapper.list(sysRole);
        page.setList(list);
        return page;
    }

    @Override
    public SysRoleDTO detail(Long id){
        SysRole sysRole = commonDao.findByPk(SysRole.class, id);
        SysRoleDTO dto = SysRoleDTO.of(sysRole);
        List<SysRoleAuth> ruleAuthList = commonDao.list(new SysRoleAuth().setRoleId(dto.getId()));
        if(!CollectionUtils.isEmpty(ruleAuthList)){
            List<SysAuth> authList = ruleAuthList.stream().map(ruleAuth -> new SysAuth().setId(ruleAuth.getAuthId())).collect(Collectors.toList());
            dto.setAuthList(authList);
        }
        return dto;
    }

    @Override
    @Transactional
    public int save(SysRoleDTO dto){
        SysRole sysRole = dto.toSave();
        int res = commonDao.insert(sysRole);
        if(!CollectionUtils.isEmpty(dto.getAuthList())){
            List<SysRoleAuth> ruleAuthList = dto.getAuthList().stream()
                    .map(auth -> new SysRoleAuth().setRoleId(sysRole.getId()).setAuthId(auth.getId())).collect(Collectors.toList());
            commonDao.insertBatch(ruleAuthList);
        }
        return res;
    }

    @Override
    @Transactional
    public int edit(SysRoleDTO dto){
        SysRole origin = commonDao.findByPk(SysRole.class, dto.getId());
        if(origin.getSystemFlag()==1){
            throw new GeneralException("系统内置角色,不允许修改!");
        }
        SysRole sysRole = dto.toEdit();
        int res = commonDao.updateByPk(sysRole);
        commonDao.delete(new SysRoleAuth().setRoleId(sysRole.getId()));
        if(!CollectionUtils.isEmpty(dto.getAuthList())){
            List<SysRoleAuth> ruleAuthList = dto.getAuthList().stream()
                    .map(auth -> new SysRoleAuth().setRoleId(sysRole.getId()).setAuthId(auth.getId())).collect(Collectors.toList());
            commonDao.insertBatch(ruleAuthList);
        }
        return res;
    }

    @Override
    @Transactional
    public int del(Long id){
        SysRole origin = commonDao.findByPk(SysRole.class, id);
        if(origin.getSystemFlag()==1){
            throw new GeneralException("系统内置角色,不允许删除!");
        }
        commonDao.delete(new SysRoleAuth().setRoleId(id));
        return commonDao.deleteByPk(SysRole.class, id);
    }


    @Override
    public PageResult<SysUser> users(SysUserRoleDTO dto){
        PageResult<SysUser> page = new PageResult<>(dto);
        page.startPage();
        List<SysUser> list = sysRoleMapper.users(dto);
        page.setList(list);
        return page;
    }
    @Override
    public boolean exists(Long roleId, Long userId){
        Long cnt = commonDao.count(new SysUserRole().setUserId(userId).setRoleId(roleId));
        return cnt>0;
    }
    @Override
    public int addUsers(SysUserRoleDTO dto){
        if(CollectionUtils.isEmpty(dto.getUserIds())){
            return 0;
        }
        List<SysUserRole> list = new ArrayList<>();
        dto.getUserIds().forEach( userId->{
            if(!exists(dto.getRoleId(),userId)){
                list.add(new SysUserRole().setUserId(userId).setRoleId(dto.getRoleId()));
            }
        });
        return commonDao.insertBatch(list);
    }
    @Override
    public int delUsers(SysUserRoleDTO dto){
        if(CollectionUtils.isEmpty(dto.getUserIds())){
            return 0;
        }
        int cnt = dto.getUserIds().stream().mapToInt(userId -> {
            return commonDao.delete(new SysUserRole().setUserId(userId).setRoleId(dto.getRoleId()));
        }).sum();
        return cnt;
    }
}