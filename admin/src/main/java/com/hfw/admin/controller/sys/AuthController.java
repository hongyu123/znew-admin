package com.hfw.admin.controller.sys;

import com.hfw.model.enums.sys.LogoutType;
import com.hfw.model.jackson.Result;
import com.hfw.model.utils.JsonMap;
import com.hfw.service.dto.LoginParam;
import com.hfw.service.dto.LoginUser;
import com.hfw.service.sys.sysAuth.MenuVO;
import com.hfw.service.sys.sysAuth.SysAuthService;
import com.hfw.service.sys.sysUser.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysAuthService sysAuthService;

    @PostMapping("/login")
    public Result<LoginUser> login(@RequestBody LoginParam loginParam) throws Exception{
        return Result.success( sysUserService.login(loginParam) );
    }

    @PostMapping("/logout")
    public Result<Void> logout(){
        LoginUser loginUser = LoginUser.getLoginUser();
        sysUserService.logout(loginUser.getId(), LogoutType.Logout);
        return Result.success();
    }

    @GetMapping("/auth/menus")
    public Result<List<MenuVO>> menus(){
        return Result.success(sysAuthService.menus());
    }

    @GetMapping("/auth/buttons")
    public Result<?> buttons(){
        LoginUser loginUser = LoginUser.getLoginUser();
        if(loginUser.isAdmin()){
            return Result.success(JsonMap.create().putVal("admin",true));
        }
        List<JsonMap> buttons = sysAuthService.buttons(loginUser.getId());
        Map<String,List<String>> buttonMap = new HashMap<>();
        for(JsonMap button : buttons){
            List<String> list = buttonMap.computeIfAbsent(button.getString("pname"), k -> new ArrayList<>());
            list.add(button.getString("name"));
        }
        return Result.success(buttonMap);
    }

}
