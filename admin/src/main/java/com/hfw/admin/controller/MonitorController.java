package com.hfw.admin.controller;

import com.hfw.admin.monitor.Server;
import com.hfw.common.support.jackson.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor")
public class MonitorController {

    /**
     * 获取服务器信息
     * @return
     * @throws Exception
     */
    @GetMapping("/server")
    public ApiResult<Server> serverInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return ApiResult.data(server);
    }

}
