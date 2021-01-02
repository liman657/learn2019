package com.liman.learn.pmp.controller.page;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * autor:liman
 * createtime:2021/1/2
 * comment:
 */
@Controller
@Slf4j
public class PageController {

    @RequestMapping("/login.html")
    public String toLoginIndex(){
        return "login";
    }

    @RequestMapping(value={"/index.html","/"})
    public String toIndexPage(){
        return "index";
    }

    @RequestMapping(value="/main.html")
    public String toMainPage(){
        return "main";
    }

    @RequestMapping(value="/403.html")
    public String toUnauthPage(){
        return "403";
    }

}
