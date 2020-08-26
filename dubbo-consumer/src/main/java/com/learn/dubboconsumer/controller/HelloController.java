package com.learn.dubboconsumer.controller;

import com.learn.springboot.dubbo.provider.api.IHelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * autor:liman
 * createtime:2020/8/25
 * comment:
 */
@RestController
public class HelloController {

    @Reference(check = false, loadbalance = "roundrobin", cluster = "failfast", timeout = 100
            , mock = "com.learn.dubboconsumer.controller.MockIHelloServiceImpl"
            , retries = 5)
    IHelloService iHelloService;

    @RequestMapping(value = "dubbohello", method = RequestMethod.GET)
    public String dubboHello(@RequestParam("name") String name) {
        return iHelloService.sayHello(name);
    }
}
