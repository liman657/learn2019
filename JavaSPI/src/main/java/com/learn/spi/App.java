package com.learn.spi;

import java.util.ServiceLoader;

/**
 * autor:liman
 * createtime:2019/9/29
 * comment:
 */
public class App {

    //我们只需要利用ServiceLoader去加载我们需要的类即可
    private static ServiceLoader<IService> services = ServiceLoader.load(IService.class) ;

    public static void main(String[] args) {
        for(IService service:services){
            service.say("hello world!");
        }
    }

}
