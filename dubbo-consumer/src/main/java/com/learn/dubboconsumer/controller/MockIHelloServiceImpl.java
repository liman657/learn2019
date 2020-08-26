package com.learn.dubboconsumer.controller;

import com.learn.springboot.dubbo.provider.api.IHelloService;

/**
 * autor:liman
 * createtime:2020/8/25
 * comment:
 */
public class MockIHelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String name) {
        String errorMessage = "服务繁忙 , "+name+" 请稍后再试";
        return errorMessage;
    }
}
