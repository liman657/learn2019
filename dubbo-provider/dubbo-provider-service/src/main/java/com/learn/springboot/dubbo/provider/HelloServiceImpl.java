package com.learn.springboot.dubbo.provider;

import com.learn.springboot.dubbo.provider.api.IHelloService;
import org.apache.dubbo.config.annotation.Service;

/**
 * autor:liman
 * createtime:2020/8/25
 * comment:
 */
@Service(loadbalance="random",timeout = 10000)
public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String name) {
//        try {
//            Thread.sleep(50_000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.printf("dubbo service is invoked param:%s\n",name);
        return "hello this is dubbo-sprintboot provider"+name;
    }
}
