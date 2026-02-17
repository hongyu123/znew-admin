package com.hfw.service.sys.sysRole;

import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.jackson.Result;
import com.hfw.model.mybatis.Where;
import com.hfw.model.po.sys.SysRole;
import com.hfw.model.po.sys.SysRoleAuth;
import com.hfw.model.po.sys.SysUser;
import com.hfw.model.po.sys.SysUserRole;
import com.hfw.service.component.CommonMapper;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.sys.sysAuth.SysAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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

    public PageResult<SysRole> page(Page<SysRole> page, SysRole po) {
        LoginUser loginUser = LoginUser.getLoginUser();
        page.startPage();
        if(loginUser.getId() == 1){
            Where<SysRole> where = Where.<SysRole>where()
                    .like(StringUtils.hasText(po.getName()), SysRole.COLUMN.name, po.getName() + "%")
                    .like(StringUtils.hasText(po.getCode()), SysRole.COLUMN.code, po.getCode() + "%");
            List<SysRole> list = sysRoleMapper.selectList(where);
            return PageResult.of(list);
        }
        po.setId(loginUser.getId());
        po.setCreateUser(loginUser.getAccount());
        List<SysRole> list = sysRoleMapper.userRoleList(page, po);
        return PageResult.of(list);
    }

    public SysRole detail(Long id){
        SysRole sysRole = sysRoleMapper.selectByPk(id);
        if(sysRole==null){
            return null;
        }
        sysRole.setAuthList(sysAuthService.roleAuths(sysRole.getId()));
        return sysRole;
    }

    @Transactional
    public void add(SysRole sysRole){
        //保存角色
        sysRoleMapper.insert(sysRole);
        //保存角色权限列表
        if(!CollectionUtils.isEmpty(sysRole.getAuthList())){
            List<SysRoleAuth> ruleAuthList = sysRole.getAuthList().stream().map(auth -> {
                SysRoleAuth sysRoleAuth = new SysRoleAuth();
                sysRoleAuth.setRoleId(sysRole.getId());
                sysRoleAuth.setAuthId(auth.getId());
                return sysRoleAuth;
            }).collect(Collectors.toList());
            commonMapper.insertBatch(ruleAuthList);
        }
    }

    @Transactional
    public Result<Void> edit(SysRole sysRole){
        SysRole origin = sysRoleMapper.selectByPk(sysRole.getId());
        //内置角色只允许超级管理员修改
        if(origin.getSystemFlag()==1){
            LoginUser loginUser = LoginUser.getLoginUser();
            if(loginUser.getId()!=1){
                return Result.error("系统内置角色,不允许修改!");
            }
        }
        //更新角色
        sysRoleMapper.updateByPk(sysRole);
        //更新角色分配的权限
        commonMapper.delete(SysRoleAuth.class, Where.<SysRoleAuth>where().eq(SysRoleAuth.COLUMN.roleId, sysRole.getId()));
        if(!CollectionUtils.isEmpty(sysRole.getAuthList())){
            List<SysRoleAuth> ruleAuthList = sysRole.getAuthList().stream().map(auth -> {
                SysRoleAuth sysRoleAuth = new SysRoleAuth();
                sysRoleAuth.setRoleId(sysRole.getId());
                sysRoleAuth.setAuthId(auth.getId());
                return sysRoleAuth;
            }).collect(Collectors.toList());
            commonMapper.insertBatch(ruleAuthList);
        }
        sysAuthService.clearCache();
        return Result.success();
    }

    @Transactional
    public Result<Void> del(Long id){
        SysRole origin = sysRoleMapper.selectByPk(id);
        if(origin.getSystemFlag()==1){
            return Result.error("系统内置角色,不允许删除!");
        }
        sysRoleMapper.deleteByPk(id);
        commonMapper.delete(SysRoleAuth.class, Where.<SysRoleAuth>where().eq(SysRoleAuth.COLUMN.roleId, id));
        sysAuthService.clearCache();
        return Result.success();
    }


    public PageResult<SysUser> users(Page<SysUser> page, Long roleId){
        page.startPage();
        List<SysUser> list = sysRoleMapper.users(page, roleId);
        return PageResult.of(list);
    }

    //角色授权用户
    public int allocateUsers(SysUserRole sysUserRole){
        if(CollectionUtils.isEmpty(sysUserRole.getUserIds())){
            return 0;
        }
        List<SysUserRole> sysUserRoleList = sysUserRole.getUserIds().stream()
                //.filter(userId -> QueryChain.of(commonMapper, SysUserRole.class).eq(SysUserRole::getUserId, userId).eq(SysUserRole::getRoleId, sysUserRole.getRoleId()).count()>0 )
                .map(userId -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(sysUserRole.getRoleId());
                    return userRole;
        }).toList();
        sysAuthService.clearCache();
        return commonMapper.insertBatch(sysUserRoleList);
    }

    //角色取消授权用户
    public int cancelUsers(SysUserRole sysUserRole){
        if(CollectionUtils.isEmpty(sysUserRole.getUserIds())){
            return 0;
        }
        sysAuthService.clearCache();

        return sysUserRole.getUserIds().stream().mapToInt(userId ->
                commonMapper.delete(SysUserRole.class, Where.<SysUserRole>where().eq(SysUserRole.COLUMN.userId,userId).eq(SysUserRole.COLUMN.roleId, sysUserRole.getRoleId()))
        ).sum();
    }

    /**
     * 获取用户的角色编码列表
     * 用于用户权限认证
     * @param id 用户id
     * @return 角色编码列表
     */
    public List<String> userRoles(Long id){
        if(id==1L){
            return List.of("*");
        }
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
            return sysRoleMapper.selectList(Where.<SysRole>where().eq(SysRole.COLUMN.state, EnableState.Enable).orderBy(SysRole.COLUMN.sort));
        }
        return sysRoleMapper.userRoles(loginUser.getId(),1, loginUser.getAccount());
    }

}