package com.hfw.basesystem.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.hfw.basesystem.dto.SysUserDTO;
import com.hfw.basesystem.entity.SysUser;
import com.hfw.basesystem.entity.SysUserRole;
import com.hfw.basesystem.mapper.SysUserMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.basesystem.service.RedisAuthService;
import com.hfw.basesystem.service.SysUserService;
import com.hfw.basesystem.vo.MenuVO;
import com.hfw.common.entity.PageResult;
import com.hfw.common.enums.EnableState;
import com.hfw.common.support.GeneralException;
import com.hfw.common.util.TreeUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统用户服务实现
 * @author farkle
 * @date 2022-12-14
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private CommonDao commonDao;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private RedisAuthService redisAuthService;

    @Override
    public PageResult<SysUser> page(SysUser sysUser) {
        PageResult<SysUser> page = new PageResult<>(sysUser);
        page.startPage();
        List<SysUser> list = sysUserMapper.list(sysUser);
        page.setList(list);
        return page;
    }
    @Override
    public SysUserDTO detail(Long id){
        SysUser sysUser = commonDao.selectByPk(SysUser.class, id);
        SysUserDTO dto = SysUserDTO.of(sysUser);
        List<SysUserRole> list = commonDao.select( SysUserRole.builder().userId(dto.getId()).build() );
        if(!CollectionUtils.isEmpty(list)){
            dto.setRoleList( list.stream().map( userRole->userRole.getRoleId() ).collect(Collectors.toList()) );
        }
        return dto;
    }

    @Override
    @Transactional
    public void save(SysUserDTO dto){
        Long cnt = commonDao.count( SysUser.builder().account(dto.getAccount()).build() );
        if(cnt>0){
            throw new GeneralException("该账号已存在!!");
        }
        SysUser sysUser = dto.saveFilter().toEntity();
        sysUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        sysUser.setCreateTime(LocalDateTime.now());
        commonDao.insert(sysUser);
        List<SysUserRole> roleList = dto.getRoleList().stream().map(id -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getId());
            userRole.setRoleId(id);
            return userRole;
        }).collect(Collectors.toList());
        commonDao.insertBatch(roleList);
    }

    @Override
    @Transactional
    public void edit(SysUserDTO dto){
        SysUser origin = commonDao.selectByPk(SysUser.class, dto.getId());
        if(origin.getId()==1 || origin.getSystemFlag()==1){
            if(!origin.getId().equals(dto.getCurrentUserId())){
                throw new GeneralException("系统内置用户,无法修改!");
            }
        }
        //修改不能修改账号和密码
        SysUser sysUser = dto.updateFilter().toEntity();
        commonDao.updateByPk(sysUser);
        if(!CollectionUtils.isEmpty(dto.getRoleList())){
            commonDao.delete( SysUserRole.builder().userId(dto.getId()).build() );
            List<SysUserRole> roleList = dto.getRoleList().stream().map(id -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(sysUser.getId());
                userRole.setRoleId(id);
                return userRole;
            }).collect(Collectors.toList());
            commonDao.insertBatch(roleList);
        }
        if(sysUser.getState()!=null && sysUser.getState() == EnableState.Disable){
            redisAuthService.disableUser(sysUser.getId());
        }
    }

    @Override
    @Transactional
    public void del(Long id){
        SysUser origin = commonDao.selectByPk(SysUser.class, id);
        if(origin.getId()==1 || origin.getSystemFlag()==1){
            throw new GeneralException("系统内置用户,无法删除!");
        }
        commonDao.delete( SysUserRole.builder().userId(id).build() );
        commonDao.deleteByPk(SysUser.class, id);
    }

    @Override
    public int changePassword(SysUserDTO dto){
        SysUser origin = commonDao.selectByPk(SysUser.class, dto.getId());
        if(!passwordEncoder.matches(dto.getOld_password(), origin.getPassword())){
            throw new GeneralException("原密码错误!");
        }
        SysUser up = new SysUser();
        up.setId(dto.getId());
        up.setPassword(passwordEncoder.encode(dto.getPassword()));
        return commonDao.updateByPk(up);
    }
    @Override
    public int resetPassword(SysUser sysUser){
        SysUser up = new SysUser();
        up.setId(sysUser.getId());
        up.setPassword(passwordEncoder.encode("123456"));
        return commonDao.updateByPk(up);
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
            if(o.containsKey("code") && o.getString("code").contains("*") ){
                return "*";
            }
            return o.containsKey("buttons") ?o.getString("buttons"):"";
        }));
        return map;
    }
    @Override
    public List<MenuVO> webMenus(Long userId){
        List<MenuVO> menus = userId==1? sysUserMapper.adminWebMenus(): sysUserMapper.webMenus(userId);
        //menus = ListUtil.listTotree(menus);
        menus = TreeUtils.listToTree(menus, MenuVO::getId, MenuVO::getParentId, MenuVO::setChildren,
                (vo) -> vo.getParentId()==0 );
        return menus;
    }
}