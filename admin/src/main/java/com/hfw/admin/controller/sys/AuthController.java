package com.hfw.admin.controller.sys;

import com.alibaba.fastjson2.JSON;
import com.hfw.service.dto.LoginParam;
import com.hfw.model.jackson.Result;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.sys.sysAuth.SysAuthService;
import com.hfw.service.sys.sysUser.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysAuthService sysAuthService;

    @PostMapping("/login")
    public Result<LoginUser> login(@RequestBody LoginParam loginParam){
        return sysUserService.login(loginParam);
    }

    @GetMapping(value = "/auth/menus", produces = MediaType.APPLICATION_JSON_VALUE)
    public String menu(){
        return JSON.toJSONString( Result.success(sysAuthService.menus()) );
    }

}
