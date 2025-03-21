package com.hfw.service.sys.sysUser;

import cn.dev33.satoken.secure.BCrypt;
import cn.xbatis.core.sql.executor.chain.DeleteChain;
import cn.xbatis.core.sql.executor.chain.QueryChain;
import com.hfw.model.entity.Page;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.jackson.Result;
import com.hfw.model.mapper.CommonMapper;
import com.hfw.model.po.sys.SysUser;
import com.hfw.model.po.sys.SysUserRole;
import com.hfw.model.utils.ValidUtil;
import com.hfw.service.dto.LoginParam;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.sys.sysAuth.SysAuthService;
import com.hfw.service.sys.sysRole.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysAuthService sysAuthService;

    public Page<SysUser> page(Page<SysUser> page, SysUser po) {
        return sysUserMapper.page(page, po);
    }

    public SysUserDTO detail(Long id){
        SysUser sysUser = sysUserMapper.getById(id);
        if(sysUser==null){
            return null;
        }
        SysUserDTO dto = SysUserDTO.of(sysUser);
        dto.setRoleList( sysRoleService.userRoleIds(dto.getId()) );
        return dto;
    }

    @Transactional
    public Result<Void> save(SysUserDTO dto){
        SysUser sysUser = dto.toPo();
        Integer cnt = QueryChain.of(sysUserMapper).eq(SysUser::getAccount, sysUser.getAccount()).count();
        if(cnt>0){
            return Result.error("该账号已存在!");
        }
        //密码加密
        String encodePassword = BCrypt.hashpw(sysUser.getPassword(), BCrypt.gensalt(12));
        sysUser.setPassword(encodePassword);
        //保存用户
        sysUserMapper.save(sysUser);
        //保存用户分配的角色
        List<SysUserRole> roleList = dto.getRoleList().stream().map(id -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getId());
            userRole.setRoleId(id);
            return userRole;
        }).collect(Collectors.toList());
        commonMapper.saveBatch(roleList);
        return Result.success();
    }

    @Transactional
    public Result<Void> edit(SysUserDTO dto){
        SysUser sysUser = dto.toPo();
        SysUser origin = sysUserMapper.getById(sysUser.getId());
        //系统内置用户校验
        if(origin.getId()==1 || origin.getSystemFlag()==1){
            //系统内置用户只能自己改自己 或 超级管理修改
            LoginUser loginUser = LoginUser.getLoginUser();
            if(loginUser.getId()!=1 || !loginUser.getId().equals(origin.getId()) ){
                return Result.error("系统内置用户,无法修改!");
            }
        }
        //账号密码不能修改
        sysUser.updateFilter();
        //更新用户
        sysUserMapper.update(sysUser);
        //更新用户分配的角色
        if(!CollectionUtils.isEmpty(dto.getRoleList())){
            DeleteChain.of(commonMapper, SysUserRole.class).eq(SysUserRole::getUserId, sysUser.getId()).execute();
            List<SysUserRole> roleList = dto.getRoleList().stream().map(id -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(sysUser.getId());
                userRole.setRoleId(id);
                return userRole;
            }).collect(Collectors.toList());
            commonMapper.saveBatch(roleList);
        }
        //启用/禁用用户
        if(sysUser.getState()!=null){
           LoginUser.enableUser(sysUser.getId(), sysUser.getState());
        }
        return Result.success();
    }

    @Transactional
    public Result<Void> del(Long id){
        SysUser origin = sysUserMapper.getById(id);
        if(origin.getId()==1 || origin.getSystemFlag()==1){
            return Result.error("系统内置用户,无法删除!");
        }
        sysUserMapper.deleteById(id);
        DeleteChain.of(commonMapper, SysUserRole.class).eq(SysUserRole::getUserId, id).execute();
        return Result.success();
    }

    public Result<Void> changePassword(SysUserDTO dto){
        if(!ValidUtil.validPassword(dto.getPassword()) ){
            return Result.error("密码格式错误!");
        }
        dto.setId(LoginUser.getLoginUser().getId());
        SysUser origin = sysUserMapper.getById(dto.getId());
        //原密码校验
        if(!BCrypt.checkpw(dto.getOldPassword(), origin.getPassword())){
            return Result.error("原密码错误!");
        }
        SysUser up = new SysUser();
        up.setId(dto.getId());
        //密码加密
        String encodePassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(12));
        up.setPassword(encodePassword);
        sysUserMapper.update(up);
        return Result.success();
    }

    public int resetPassword(SysUser sysUser){
        SysUser up = new SysUser();
        up.setId(sysUser.getId());
        //密码加密
        String encodePassword = BCrypt.hashpw("123456", BCrypt.gensalt(12));
        up.setPassword(encodePassword);
        return sysUserMapper.update(up);
    }

    /**
     * 用户登录
     * @param loginParam 登录参数
     * @return 登录信息
     */
    public Result<LoginUser> login(LoginParam loginParam){
        SysUser sysUser = QueryChain.of(sysUserMapper).eq(SysUser::getAccount, loginParam.getUsername()).get();
        if(sysUser==null){
            return Result.error("用户名/密码错误!");
        }
        if(EnableState.Enable != sysUser.getState()){
            return Result.error("被禁用的账号!");
        }
        if(!BCrypt.checkpw(loginParam.getPassword(), sysUser.getPassword())){
            return Result.error("用户名/密码错误!");
        }
        LoginUser loginUser = LoginUser.of(sysUser);
        loginUser.setPermissions(sysAuthService.userAuths(loginUser.getId()));
        LoginUser.store(loginUser);
        return Result.success(loginUser);
    }

}
