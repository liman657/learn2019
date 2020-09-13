package com.learn.springauthserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * autor:liman
 * createtime:2019/12/10
 * comment:测试页面跳转
 */
@Controller
@Slf4j
@RequestMapping("page")
public class PageController {

    @RequestMapping("testPage")
    public String testPage(ModelMap map){
        map.put("code","test");
        return "page";
    }

}
