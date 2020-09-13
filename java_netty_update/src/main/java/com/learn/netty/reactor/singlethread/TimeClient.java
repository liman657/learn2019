package com.learn.netty.reactor.singlethread;

import com.learn.netty.reactor.multithread.TimeClientHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * autor:liman
 * createtime:2020/8/12
 * comment:
 */
@Slf4j
public class TimeClient {

    public static void main(String[] args) throws InterruptedException {
        int port = 9898;
        long startTime = System.currentTimeMillis();
        for(int i=0;i<1000;i++) {
            Thread thread = new Thread(new TimeClientHandler("127.0.0.1", port), "TimeClientThread-001" + i);
            thread.start();
            thread.join();
        }
        long endTime = System.currentTimeMillis();
        long spendTime = endTime-startTime;

        log.info("耗时:{}",spendTime);
    }

}
