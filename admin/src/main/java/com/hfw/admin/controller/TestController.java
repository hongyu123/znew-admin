package com.hfw.admin.controller;

import cn.xbatis.core.mybatis.mapper.context.Pager;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hfw.model.entity.Page;
import com.hfw.model.jackson.Result;
import com.hfw.service.sys.gen.GenMapper;
import com.hfw.service.sys.gen.Table;
import com.hfw.service.sys.sysAuth.MenuVO;
import com.hfw.service.sys.sysAuth.SysAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class TestController {

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(String username, String password){
        String res = "{\n" +
                "  \"success\": true,\n" +
                "  \"data\": {\n" +
                "    \"avatar\": \"https://avatars.githubusercontent.com/u/44761321\",\n" +
                "    \"username\": \"admin\",\n" +
                "    \"nickname\": \"小铭\",\n" +
                "    \"roles\": [\n" +
                "      \"admin\"\n" +
                "    ],\n" +
                "    \"permissions\": [\n" +
                "      \"*:*:*\"\n" +
                "    ],\n" +
                "    \"accessToken\": \"eyJhbGciOiJIUzUxMiJ9.admin\",\n" +
                "    \"refreshToken\": \"eyJhbGciOiJIUzUxMiJ9.adminRefresh\",\n" +
                "    \"expires\": \"2030/10/30 00:00:00\"\n" +
                "  }\n" +
                "}";
        return res;
    }

    @Autowired
    private SysAuthService sysAuthService;
    @GetMapping(value = "/get-async-routes", produces = MediaType.APPLICATION_JSON_VALUE)
    public String menu(){
        return JSON.toJSONString( Result.success(sysAuthService.menus()) );
    }

    @Autowired
    private GenMapper genMapper;

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test() throws JsonProcessingException {
        Page<Table> page = genMapper.tableList(Page.of(1,1), "znew", null);
        return JSON.toJSONString(page.getList());
    }

}
