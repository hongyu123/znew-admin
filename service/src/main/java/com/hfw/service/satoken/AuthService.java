package com.hfw.service.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.hfw.service.sys.sysAuth.SysAuthService;
import com.hfw.service.sys.sysRole.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthService implements StpInterface {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysAuthService sysAuthService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        Long id = Long.valueOf((String) loginId);
        return sysAuthService.userAuths(id);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Long id = Long.valueOf((String) loginId);
        return sysRoleService.userRoles(id);
    }

}
