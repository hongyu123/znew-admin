package com.hfw.service.sys.sysUser;

import cn.dev33.satoken.secure.BCrypt;
import com.hfw.model.entity.Page;
import com.hfw.model.entity.PageResult;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.jackson.Result;
import com.hfw.model.mybatis.Where;
import com.hfw.model.po.sys.SysUser;
import com.hfw.model.po.sys.SysUserRole;
import com.hfw.model.utils.ValidUtil;
import com.hfw.service.component.CommonMapper;
import com.hfw.service.dto.LoginParam;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.sys.sysAuth.SysAuthService;
import com.hfw.service.sys.sysLoginLog.SysLoginLogService;
import com.hfw.service.sys.sysRole.SysRoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    @Autowired
    private SysLoginLogService sysLoginLogService;

    public PageResult<SysUser> page(Page<SysUser> page, SysUser po) {
        page.startPage();
        List<SysUser> list = sysUserMapper.page(page, po);
        return PageResult.of(list);
    }

    public SysUserDTO detail(Long id){
        SysUser sysUser = sysUserMapper.selectByPk(id);
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
        long cnt = sysUserMapper.count(Where.<SysUser>where().eq(SysUser.COLUMN.account, sysUser.getAccount()));
        if(cnt>0){
            return Result.error("该账号已存在!");
        }
        //密码加密
        String encodePassword = BCrypt.hashpw(sysUser.getPassword(), BCrypt.gensalt(12));
        sysUser.setPassword(encodePassword);
        //保存用户
        sysUserMapper.insert(sysUser);
        //保存用户分配的角色
        List<SysUserRole> roleList = dto.getRoleList().stream().map(id -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getId());
            userRole.setRoleId(id);
            return userRole;
        }).collect(Collectors.toList());
        commonMapper.insertBatch(roleList);
        return Result.success();
    }

    @Transactional
    public Result<Void> edit(SysUserDTO dto){
        SysUser sysUser = dto.toPo();
        SysUser origin = sysUserMapper.selectByPk(sysUser.getId());
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
        sysUserMapper.updateByPk(sysUser);
        //更新用户分配的角色
        if(!CollectionUtils.isEmpty(dto.getRoleList())){
            commonMapper.delete(SysUserRole.class, Where.<SysUserRole>where().eq(SysUserRole.COLUMN.userId, sysUser.getId()));
            List<SysUserRole> roleList = dto.getRoleList().stream().map(id -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(sysUser.getId());
                userRole.setRoleId(id);
                return userRole;
            }).collect(Collectors.toList());
            commonMapper.insertBatch(roleList);
        }
        //启用/禁用用户
        if(sysUser.getState()!=null){
           LoginUser.enableUser(sysUser.getId(), sysUser.getState());
        }
        sysAuthService.clearCache();
        return Result.success();
    }

    @Transactional
    public Result<Void> del(Long id){
        SysUser origin = sysUserMapper.selectByPk(id);
        if(origin.getId()==1 || origin.getSystemFlag()==1){
            return Result.error("系统内置用户,无法删除!");
        }
        sysUserMapper.deleteByPk(id);
        commonMapper.delete(SysUserRole.class, Where.<SysUserRole>where().eq(SysUserRole.COLUMN.userId, id));
        sysAuthService.clearCache();
        return Result.success();
    }

    public Result<Void> changePassword(String oldPassword, String password){
        if(!ValidUtil.validPassword(password) ){
            return Result.error("密码格式错误!");
        }
        if(oldPassword.equals(password)){
            return Result.error("新密码不能与原密码一致");
        }
        Long userId = LoginUser.getLoginUser().getId();
        SysUser origin = sysUserMapper.selectByPk(userId);
        //原密码校验
        if(!BCrypt.checkpw(oldPassword, origin.getPassword())){
            return Result.error("原密码错误!");
        }
        SysUser up = new SysUser();
        up.setId(userId);
        //密码加密
        String encodePassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        up.setPassword(encodePassword);
        sysUserMapper.updateByPk(up);
        LoginUser.logout();
        return Result.success();
    }

    public int resetPassword(Long userId){
        SysUser up = new SysUser();
        up.setId(userId);
        //密码加密
        String encodePassword = BCrypt.hashpw("123456", BCrypt.gensalt(12));
        up.setPassword(encodePassword);
        return sysUserMapper.updateByPk(up);
    }

    /**
     * 用户登录
     * @param loginParam 登录参数
     * @return 登录信息
     */
    public Result<LoginUser> login(LoginParam loginParam){
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        SysUser sysUser = sysUserMapper.selectOne(Where.<SysUser>where().eq(SysUser.COLUMN.account, loginParam.getUsername()));
        if(sysUser==null){
            sysLoginLogService.login(null,loginParam.getUsername(),"用户名不存在", request);
            return Result.error("用户名/密码错误!");
        }
        if(EnableState.Enable != sysUser.getState()){
            sysLoginLogService.login(null,loginParam.getUsername(),"被禁用的账号", request);
            return Result.error("被禁用的账号!");
        }
        if(!BCrypt.checkpw(loginParam.getPassword(), sysUser.getPassword())){
            sysLoginLogService.login(null,loginParam.getUsername(),"密码错误", request);
            return Result.error("用户名/密码错误!");
        }
        LoginUser loginUser = LoginUser.of(sysUser);
        loginUser.setRoles(sysRoleService.userRoles(loginUser.getId()));
        loginUser.setPermissions(sysAuthService.userAuths(loginUser.getId()));
        LoginUser.store(loginUser);
        sysLoginLogService.login(loginUser.getAccessToken(),loginUser.getUsername(),"登录成功", request);
        return Result.success(loginUser);
    }

    public SysUser userInfo(){
        LoginUser loginUser = LoginUser.getLoginUser();
        return sysUserMapper.selectByPk(loginUser.getId());
    }
    public int editInfo(SysUser sysUser){
        LoginUser loginUser = LoginUser.getLoginUser();
        SysUser up = new SysUser();
        up.setId(loginUser.getId());
        up.setAvatar(sysUser.getAvatar());
        up.setNickname(sysUser.getNickname());
        up.setPhone(sysUser.getPhone());
        up.setEmail(sysUser.getEmail());
        up.setGender(sysUser.getGender());
        up.setRemark(sysUser.getRemark());
        return sysUserMapper.updateByPk(up);
    }

}
