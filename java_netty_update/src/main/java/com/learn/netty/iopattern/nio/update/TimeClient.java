package com.learn.netty.iopattern.nio.update;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/8/12
 * comment:
 */
@Slf4j
public class TimeClient {

    public static void main(String[] args) {
        int port = 8999;
        new Thread(new TimeClientHandler("127.0.0.1",port),"TimeClientThread-001").start();
    }

}
