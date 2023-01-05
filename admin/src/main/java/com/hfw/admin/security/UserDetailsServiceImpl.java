package com.hfw.admin.security;

import com.hfw.basesystem.entity.SysAuth;
import com.hfw.basesystem.entity.SysUser;
import com.hfw.basesystem.mapper.SysUserMapper;
import com.hfw.basesystem.mybatis.CommonDao;
import com.hfw.common.enums.EnableState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zyh
 * @date 2022-04-12
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CommonDao commonDao;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = commonDao.findOne(new SysUser().setAccount(username));
        if(sysUser==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        if(EnableState.Enable != sysUser.getState()){
            throw new DisabledException("被禁用的账号!");
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setId(sysUser.getId());
        loginUser.setUsername(sysUser.getAccount());
        loginUser.setNickname(sysUser.getNickname());
        loginUser.setAvatar(sysUser.getAvatar());
        loginUser.setPassword(sysUser.getPassword());
        loginUser.setEnabled(EnableState.Enable == sysUser.getState());

        List<SysAuth> authList = sysUserMapper.authList(loginUser.getId());
        if(!CollectionUtils.isEmpty(authList)){
            Set<String> permission = new HashSet<>();
            authList.forEach( auth->{
                if(StringUtils.hasText(auth.getCode())){
                    permission.add(auth.getCode());
                }
                if(StringUtils.hasText(auth.getContainsCode())){
                    String[] arr = auth.getContainsCode().split(",");
                    for(String s : arr){
                        permission.add(s);
                    }
                }
            });
            loginUser.setPermission(permission);
            /*List<CodeGrantedAuthority> list = authList.stream().filter(auth-> StringUtils.hasText(auth.getCode()))
                    .map(auth -> new CodeGrantedAuthority(auth.getCode())).collect(Collectors.toList());
            loginUser.setAuthorities(list);*/
        }
        return loginUser;
    }


}
