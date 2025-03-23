package com.hfw.admin.controller.znew;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hfw.admin.service.TestService;
import com.hfw.service.dto.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @Autowired
    private TestService testService;
    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test() throws JsonProcessingException {
        testService.test();
        return "ok";
    }

}
