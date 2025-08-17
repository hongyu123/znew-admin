package com.hfw.admin.controller.znew;

import com.hfw.model.jackson.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<Void> test() throws Exception {
        return Result.success();
    }

}
