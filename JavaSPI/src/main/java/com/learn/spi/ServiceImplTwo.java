package com.learn.spi;

/**
 * autor:liman
 * createtime:2019/9/29
 * comment:
 */
public class ServiceImplTwo implements IService {
    public void say(String word) {
        System.out.println(this.getClass().toString()+" say:"+word);
    }
}
