package com.hfw.service.sys.sysRole;

import cn.xbatis.core.sql.executor.chain.DeleteChain;
import cn.xbatis.core.sql.executor.chain.QueryChain;
import com.hfw.model.entity.Page;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.jackson.Result;
import com.hfw.model.mapper.CommonMapper;
import com.hfw.model.po.sys.SysRole;
import com.hfw.model.po.sys.SysRoleAuth;
import com.hfw.model.po.sys.SysUser;
import com.hfw.model.po.sys.SysUserRole;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.sys.sysAuth.SysAuthService;
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
    @Autowired
    private SysAuthService sysAuthService;

    public Page<SysRole> page(Page<SysRole> page, SysRole po) {
        return sysRoleMapper.page(page, po);
    }

    public SysRole detail(Long id){
        SysRole sysRole = sysRoleMapper.getById(id);
        if(sysRole==null){
            return null;
        }
        sysRole.setAuthList(sysAuthService.userAuthList(sysRole.getId()));
        return sysRole;
    }

    @Transactional
    public void add(SysRole sysRole){
        //保存角色
        sysRoleMapper.save(sysRole);
        //保存角色权限列表
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
        //内置角色只允许超级管理员修改
        if(origin.getSystemFlag()==1){
            LoginUser loginUser = LoginUser.getLoginUser();
            if(loginUser.getId()!=1){
                return Result.error("系统内置角色,不允许修改!");
            }
        }
        //更新角色
        sysRoleMapper.update(sysRole);
        //更新角色分配的权限
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

    /**
     * 获取用户的角色编码列表
     * 用于用户权限认证
     * @param id 用户id
     * @return 角色编码列表
     */
    public List<String> userRoles(Long id){
        List<SysRole> roleList = sysRoleMapper.userRoles(id,0, "");
        return roleList.stream().map(SysRole::getCode).toList();
    }

    /**
     * 获取用户的角色ID列表
     * 用于用户详情
     * @param id 用户id
     * @return 角色ID列表
     */
    public List<Long> userRoleIds(Long id){
        List<SysRole> roleList = sysRoleMapper.userRoles(id,0, "");
        return roleList.stream().map(SysRole::getId).toList();
    }

    /**
     * 获取当前用户的角色列表(包含自建的)
     * 用于给其他人分配角色
     * @return 角色列表
     */
    public List<SysRole> currentUserRolesWithOwn(){
        LoginUser loginUser = LoginUser.getLoginUser();
        if(loginUser.getId() == 1){
             return QueryChain.of(sysRoleMapper).eq(SysRole::getState, EnableState.Enable).list();
        }
        return sysRoleMapper.userRoles(loginUser.getId(),1, loginUser.getAccount());
    }

}