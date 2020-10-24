package com.learn.netty_im.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * autor:liman
 * createtime:2020/9/27
 * comment:
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String helloController(){
        return "hello";
    }

}
