package com.hfw.service.sys.sysRole;

import cn.xbatis.core.sql.executor.chain.DeleteChain;
import cn.xbatis.core.sql.executor.chain.QueryChain;
import com.hfw.model.entity.Page;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.jackson.Result;
import com.hfw.model.mapper.CommonMapper;
import com.hfw.model.po.sys.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色服务实现
 * @author farkle
 * @date 2022-12-14
 */
@Service
public class SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private CommonMapper commonMapper;

    public Page<SysRole> page(Page<SysRole> page, SysRole po) {
        return sysRoleMapper.page(page, po);
    }

    public SysRole detail(Long id){
        SysRole sysRole = sysRoleMapper.getById(id);
        if(sysRole==null){
            return null;
        }
        List<SysRoleAuth> ruleAuthList = QueryChain.of(commonMapper, SysRoleAuth.class).eq(SysRoleAuth::getRoleId, id).list();
        if(!CollectionUtils.isEmpty(ruleAuthList)){
            List<SysAuth> authList = ruleAuthList.stream().map(ruleAuth -> {
                SysAuth sysAuth = new SysAuth();
                sysAuth.setId(ruleAuth.getAuthId());
                return sysAuth;
            }).collect(Collectors.toList());
            sysRole.setAuthList(authList);
        }
        return sysRole;
    }

    @Transactional
    public void add(SysRole sysRole){
        sysRoleMapper.save(sysRole);
        if(!CollectionUtils.isEmpty(sysRole.getAuthList())){
            List<SysRoleAuth> ruleAuthList = sysRole.getAuthList().stream().map(auth -> {
                SysRoleAuth sysRoleAuth = new SysRoleAuth();
                sysRoleAuth.setRoleId(sysRole.getId());
                sysRoleAuth.setAuthId(auth.getId());
                return sysRoleAuth;
            }).collect(Collectors.toList());
            commonMapper.saveBatch(ruleAuthList);
        }
    }

    @Transactional
    public Result<Void> edit(SysRole sysRole){
        SysRole origin = sysRoleMapper.getById(sysRole.getId());
        if(origin.getSystemFlag()==1){
            return Result.error("系统内置角色,不允许修改!");
        }
        sysRoleMapper.update(sysRole);
        DeleteChain.of(commonMapper, SysRoleAuth.class).eq(SysRoleAuth::getRoleId, sysRole.getId()).execute();
        if(!CollectionUtils.isEmpty(sysRole.getAuthList())){
            List<SysRoleAuth> ruleAuthList = sysRole.getAuthList().stream().map(auth -> {
                SysRoleAuth sysRoleAuth = new SysRoleAuth();
                sysRoleAuth.setRoleId(sysRole.getId());
                sysRoleAuth.setAuthId(auth.getId());
                return sysRoleAuth;
            }).collect(Collectors.toList());
            commonMapper.saveBatch(ruleAuthList);
        }
        return Result.success();
    }

    @Transactional
    public Result<Void> del(Long id){
        SysRole origin = sysRoleMapper.getById(id);
        if(origin.getSystemFlag()==1){
            return Result.error("系统内置角色,不允许删除!");
        }
        sysRoleMapper.deleteById(id);
        DeleteChain.of(commonMapper, SysRoleAuth.class).eq(SysRoleAuth::getRoleId, id).execute();
        return Result.success();
    }

    public List<SysRole> list(EnableState state){
        return QueryChain.of(sysRoleMapper).eq(state!=null, SysRole::getState, state).list();
    }

    public Page<SysUser> users(Page<SysUser> page){
        return sysRoleMapper.users(page);
    }

    public boolean exists(Long roleId, Long userId){
        Integer cnt = QueryChain.of(commonMapper, SysUserRole.class).eq(SysUserRole::getUserId, userId).eq(SysUserRole::getRoleId, roleId).count();
        return cnt>0;
    }

    public int addUsers(SysUserRole sysUserRole){
        if(CollectionUtils.isEmpty(sysUserRole.getUserIds())){
            return 0;
        }
        List<SysUserRole> sysUserRoleList = sysUserRole.getUserIds().stream()
                .filter(userId -> !exists(sysUserRole.getRoleId(), userId))
                .map(userId -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(sysUserRole.getRoleId());
                    return userRole;
        }).toList();
        return commonMapper.saveBatch(sysUserRoleList);
    }

    public int delUsers(SysUserRole sysUserRole){
        if(CollectionUtils.isEmpty(sysUserRole.getUserIds())){
            return 0;
        }
        return sysUserRole.getUserIds().stream().mapToInt(userId -> {
            return DeleteChain.of(commonMapper, SysUserRole.class).eq(SysUserRole::getUserId, userId).eq(SysUserRole::getRoleId, sysUserRole.getRoleId()).execute();
        }).sum();
    }

}