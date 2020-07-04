package com.learn.thread.update.phase01.threadapi;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

/**
 * autor:liman
 * createtime:2020/6/7
 * comment:join的简单实例
 */
@Slf4j
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            IntStream.rangeClosed(1,999).forEach(i->log.info("this is t1 thread,i:{}",i));
        },"t1");

//        Thread t2 = new Thread(()->{
//            IntStream.rangeClosed(1,999).forEach(i->log.info("this is t2 thread,i:{}",i));
//        },"t2");

        t1.start();
//        t2.start();

//        t1.join();
//        t2.join();
        IntStream.rangeClosed(1,999).forEach(i->log.info("this is main thread,i:{}",i));
//        log.info("this is main Thread");

        Thread t3 = new Thread(){
            @Override
            public void run() {
                super.run();
            }
        };

        Thread t4 = new Thread(()->{});

    }
}
