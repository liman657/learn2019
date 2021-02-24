package com.learn.seckill.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * autor:liman
 * createtime:2021/2/21
 * comment:测试controller
 */
@Controller
@Slf4j
@RequestMapping("/test")
public class TestController {


    @RequestMapping("helloworld")
    public String test(){
        log.info("测试请求进入");
        return "helloworld";
    }

}
