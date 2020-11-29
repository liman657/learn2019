package com.learn.spring.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * autor:liman
 * createtime:2020/11/8
 * comment:
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String hello(){
        return "index";
    }
}
