package com.web10.music.controller;

import com.web10.music.commons.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试控制类")
@RestController
public class TestController {
    @GetMapping("/test")
    public Result test() {
        System.out.println("测试成功");
        return Result.ok("测试成功");
    }
}
