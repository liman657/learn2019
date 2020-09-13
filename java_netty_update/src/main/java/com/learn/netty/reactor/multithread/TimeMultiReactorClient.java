package com.learn.netty.reactor.multithread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * autor:liman
 * createtime:2020/8/12
 * comment:
 */
@Slf4j
public class TimeMultiReactorClient {

    static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws InterruptedException {
        int port = 10229;
        for(int i=0;i<100;i++) {
            Thread thread = new Thread(new TimeClientHandler("127.0.0.1", port), "TimeClientThread-" + i);
            pool.execute(thread);
        }
    }
}
