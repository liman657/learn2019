package com.learn.netty.reactor;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/8/12
 * comment:时间服务器
 */
@Slf4j
public class TimeServer {

    public static void main(String[] args) {
        int port = 8999;
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer,"NIO-Time-HandlerServer-001").start();
    }

}
