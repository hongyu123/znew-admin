package com.hfw.service.sys.sysUser;

import cn.xbatis.core.sql.executor.chain.DeleteChain;
import cn.xbatis.core.sql.executor.chain.QueryChain;
import com.hfw.model.entity.Page;
import com.hfw.model.enums.sys.EnableState;
import com.hfw.model.jackson.Result;
import com.hfw.model.mapper.CommonMapper;
import com.hfw.model.po.sys.SysUser;
import com.hfw.model.po.sys.SysUserRole;
import com.hfw.model.utils.ValidUtil;
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

    public Page<SysUser> page(Page<SysUser> page, SysUser po) {
        return sysUserMapper.page(page, po);
    }

    public SysUserDTO detail(Long id){
        SysUser sysUser = sysUserMapper.getById(id);
        if(sysUser==null){
            return null;
        }
        SysUserDTO dto = SysUserDTO.of(sysUser);
        List<SysUserRole> userRoleList = QueryChain.of(commonMapper, SysUserRole.class).eq(SysUserRole::getUserId, id).list();
        if(!CollectionUtils.isEmpty(userRoleList)){
            dto.setRoleList( userRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()) );
        }
        return dto;
    }

    @Transactional
    public Result<Void> save(SysUserDTO dto){
        SysUser sysUser = dto.toPo();
        Integer cnt = QueryChain.of(sysUserMapper).eq(SysUser::getAccount, sysUser.getAccount()).count();
        if(cnt>0){
            return Result.error("该账号已存在!");
        }
        //TODO:BCrypt加密
        sysUser.setPassword(sysUser.getPassword());
        sysUserMapper.save(sysUser);
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
        if(origin.getId()==1 || origin.getSystemFlag()==1){
            //TODO:当前登录用户校验
            /*if(!origin.getId().equals(dto.getCurrentUserId())){
                throw new GeneralException("系统内置用户,无法修改!");
            }*/
        }
        //账号密码不能修改
        sysUser.updateFilter();
        sysUserMapper.update(sysUser);
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
        if(sysUser.getState()!=null && sysUser.getState() == EnableState.Disable){
            //TODO:禁用用户
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
        //dto.setId(LoginUser.getLoginUser().getId());
        SysUser origin = sysUserMapper.getById(dto.getId());
        //TODO:原密码校验
        /*if(!passwordEncoder.matches(dto.getOld_password(), origin.getPassword())){
            throw new GeneralException("原密码错误!");
        }*/
        SysUser up = new SysUser();
        up.setId(dto.getId());
        //TODO:BCrypt加密
        //up.setPassword(passwordEncoder.encode(dto.getPassword()));
        up.setPassword(dto.getPassword());
        sysUserMapper.update(up);
        return Result.success();
    }

    public int resetPassword(SysUser sysUser){
        SysUser up = new SysUser();
        up.setId(sysUser.getId());
        //TODO:BCrypt加密
        //up.setPassword(passwordEncoder.encode("123456"));
        up.setPassword(sysUser.getPassword());
        return sysUserMapper.update(up);
    }

}
