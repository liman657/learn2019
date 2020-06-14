package com.learn.thread.update.stepin;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/6/3
 * comment:模板方法的实例
 */
@Slf4j
public abstract class AbstractTemplate {

    //具体的实现交给子类
    public abstract String sayHello(String name);

    public void printHelloMessage(String name){
        log.info("print hello message : {}",sayHello(name));
    }

}
