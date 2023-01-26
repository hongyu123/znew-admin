package com.hfw.admin.controller.sys;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hfw.admin.security.LoginUser;
import com.hfw.basesystem.service.SysUserService;
import com.hfw.common.support.jackson.ApiResult;
import javax.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author farkle
 * @date 2022-12-16
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private SysUserService sysUserService;

    //查询当前用户按钮权限
    @GetMapping("/buttons")
    public ApiResult buttons(){
        LoginUser loginUser = LoginUser.getLoginUser();
        return ApiResult.data( sysUserService.webMenuButtons(loginUser.getId()) );
    }

    //查询当前用户菜单权限
    @GetMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_VALUE)
    public String menu() throws JsonProcessingException {
        LoginUser loginUser = LoginUser.getLoginUser();
        return ApiResult.jsonList( sysUserService.webMenus(loginUser.getId()), ApiResult.includeFilter, "path","name","component","meta","children" );
    }

}
