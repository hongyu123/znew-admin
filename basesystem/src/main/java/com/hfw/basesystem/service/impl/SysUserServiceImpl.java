package com.hfw.basesystem.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.hfw.basesystem.dto.SysUserDTO;
import com.hfw.basesystem.entity.SysUser;
import com.hfw.basesystem.entity.SysUserRole;
import com.hfw.basesystem.mapper.SysUserMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.SysUserService;
import com.hfw.basesystem.vo.MenuVO;
import com.hfw.common.entity.PageResult;
import com.hfw.common.enums.EnableState;
import com.hfw.common.support.GeneralException;
import com.hfw.common.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统用户服务实现
 * @author farkle
 * @date 2022-12-14
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private CommonDao commonDao;

    @Override
    public PageResult<SysUser> page(SysUser sysUser) {
        PageResult<SysUser> page = new PageResult<>(sysUser);
        page.startPage();
        List<SysUser> list = sysUserMapper.list(sysUser);
        page.setList(list);
        return page;
    }
    public SysUserDTO detail(Long id){
        SysUser sysUser = commonDao.findByPk(SysUser.class, id);
        SysUserDTO dto = SysUserDTO.of(sysUser);
        List<SysUserRole> list = commonDao.list(new SysUserRole().setUserId(dto.getId()));
        if(!CollectionUtils.isEmpty(list)){
            dto.setRoleList( list.stream().map( userRole->userRole.getRoleId() ).collect(Collectors.toList()) );
        }
        return dto;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Transactional
    public int save(SysUserDTO dto){
        Long cnt = commonDao.count(new SysUser().setAccount(dto.getAccount()));
        if(cnt>0){
            throw new GeneralException("该账号已存在!!");
        }
        SysUser sysUser = dto.toSave();
        sysUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        int res = commonDao.insert(sysUser);
        List<SysUserRole> roleList = dto.getRoleList().stream()
                .map(id -> new SysUserRole().setUserId(sysUser.getId()).setRoleId(id)).collect(Collectors.toList());
        commonDao.insertBatch(roleList);
        return res;
    }
    @Autowired
    private RedisAuth redisAuth;
    @Transactional
    public int edit(SysUserDTO dto){
        SysUser origin = commonDao.findByPk(SysUser.class, dto.getId());
        if(origin.getId()==1 || origin.getSystemFlag()==1){
            if(!origin.getId().equals(dto.getCurrentUserId())){
                throw new GeneralException("系统内置用户,无法修改!");
            }
        }
        //修改不能修改账号和密码
        SysUser sysUser = dto.toEdit();
        int res = commonDao.updateByPk(sysUser);
        if(!CollectionUtils.isEmpty(dto.getRoleList())){
            commonDao.delete(new SysUserRole().setUserId(dto.getId()));
            List<SysUserRole> roleList = dto.getRoleList().stream()
                    .map(id -> new SysUserRole().setUserId(sysUser.getId()).setRoleId(id)).collect(Collectors.toList());
            commonDao.insertBatch(roleList);
        }
        if(sysUser.getState()!=null && sysUser.getState() == EnableState.Disable){
            redisAuth.disableUser(sysUser.getId());
        }
        return res;
    }
    @Override
    public int changePassword(SysUserDTO dto){
        SysUser origin = commonDao.findByPk(SysUser.class, dto.getId());
        if(!passwordEncoder.matches(dto.getOld_password(), origin.getPassword())){
            throw new GeneralException("原密码错误!");
        }
        SysUser up = new SysUser().setId(dto.getId());
        up.setPassword(passwordEncoder.encode(dto.getPassword()));
        return commonDao.updateByPk(up);
    }
    @Override
    public int resetPassword(SysUser sysUser){
        SysUser up = new SysUser().setId(sysUser.getId());
        up.setPassword(passwordEncoder.encode("123456"));
        return commonDao.updateByPk(up);
    }
    @Transactional
    public int del(SysUserDTO dto){
        SysUser origin = commonDao.findByPk(SysUser.class, dto.getId());
        if(origin.getId()==1 || origin.getSystemFlag()==1){
            throw new GeneralException("系统内置用户,无法删除!");
        }
        commonDao.delete(new SysUserRole().setUserId(dto.getId()));
        return commonDao.deleteByPk(SysUser.class, dto.getId());
    }


    @Override
    public Map<String, Object> webMenuButtons(Long userId){
        if(userId==1){
            List<MenuVO> menuList = sysUserMapper.adminWebMenus();
            Map<String, Object> map = menuList.stream().collect(Collectors.toMap(o -> o.getWebCode(), o -> "*" ));
            return map;
        }
        List<JSONObject> menuList = sysUserMapper.webMenuButtons(userId);
        Map<String, Object> map = menuList.stream().collect(Collectors.toMap(o -> o.getString("web_code"), o -> {
            String code = o.getString("code");
            if(code!=null && code.contains("*") ){
                return "*";
            }
            return o.getString("buttons");
        }));
        return map;
    }
    @Override
    public List<MenuVO> webMenus(Long userId){
        List<MenuVO> menus = userId==1? sysUserMapper.adminWebMenus(): sysUserMapper.webMenus(userId);
        menus = ListUtil.listTotree(menus);
        return menus;
    }
}