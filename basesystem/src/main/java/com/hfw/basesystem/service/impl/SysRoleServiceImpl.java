package com.hfw.basesystem.service.impl;

import com.hfw.basesystem.dto.SysRoleDTO;
import com.hfw.basesystem.dto.SysUserRoleDTO;
import com.hfw.basesystem.entity.*;
import com.hfw.basesystem.mapper.SysRoleMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.SysRoleService;
import com.hfw.common.entity.PageResult;
import com.hfw.common.support.GeneralException;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色服务实现
 * @author farkle
 * @date 2022-12-14
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
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
        SysRole sysRole = commonDao.selectByPk(SysRole.class, id);
        SysRoleDTO dto = SysRoleDTO.of(sysRole);
        List<SysRoleAuth> ruleAuthList = commonDao.select(new SysRoleAuth().setRoleId(dto.getId()));
        if(!CollectionUtils.isEmpty(ruleAuthList)){
            List<SysAuth> authList = ruleAuthList.stream().map(ruleAuth -> new SysAuth().setId(ruleAuth.getAuthId())).collect(Collectors.toList());
            dto.setAuthList(authList);
        }
        return dto;
    }

    @Override
    @Transactional
    public void save(SysRoleDTO dto){
        SysRole sysRole = dto.saveFilter().toEntity();
        commonDao.insert(sysRole);
        if(!CollectionUtils.isEmpty(dto.getAuthList())){
            List<SysRoleAuth> ruleAuthList = dto.getAuthList().stream()
                    .map(auth -> new SysRoleAuth().setRoleId(sysRole.getId()).setAuthId(auth.getId())).collect(Collectors.toList());
            commonDao.insertBatch(ruleAuthList);
        }
    }

    @Override
    @Transactional
    public void edit(SysRoleDTO dto){
        SysRole origin = commonDao.selectByPk(SysRole.class, dto.getId());
        if(origin.getSystemFlag()==1){
            throw new GeneralException("系统内置角色,不允许修改!");
        }
        SysRole sysRole = dto.updateFilter().toEntity();
        commonDao.updateByPk(sysRole);
        commonDao.delete(new SysRoleAuth().setRoleId(sysRole.getId()));
        if(!CollectionUtils.isEmpty(dto.getAuthList())){
            List<SysRoleAuth> ruleAuthList = dto.getAuthList().stream()
                    .map(auth -> new SysRoleAuth().setRoleId(sysRole.getId()).setAuthId(auth.getId())).collect(Collectors.toList());
            commonDao.insertBatch(ruleAuthList);
        }
    }

    @Override
    @Transactional
    public void del(Long id){
        SysRole origin = commonDao.selectByPk(SysRole.class, id);
        if(origin.getSystemFlag()==1){
            throw new GeneralException("系统内置角色,不允许删除!");
        }
        commonDao.delete(new SysRoleAuth().setRoleId(id));
        commonDao.deleteByPk(SysRole.class, id);
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