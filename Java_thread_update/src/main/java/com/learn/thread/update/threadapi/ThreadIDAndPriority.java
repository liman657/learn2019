package com.learn.thread.update.threadapi;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * autor:liman
 * createtime:2020/6/7
 * comment:线程id和线程优先级
 */
@Slf4j
public class ThreadIDAndPriority {
    public static void main(String[] args) {
//        Thread t = new Thread(()->{
//            Optional.of("hello").ifPresent(System.out::println);
//            try {
//                Thread.sleep(100000L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },"t1");
//
//        t.start();
//        Optional.of(t.getName()).ifPresent(System.out::println);
//        Optional.of(t.getId()).ifPresent(System.out::println);
//        Optional.of(t.getPriority()).ifPresent(System.out::println);

        Thread t1 = new Thread(()->{
           while(true){
               log.info("this is t1 thread");
           }
        });
        t1.setPriority(3);

        Thread t2 = new Thread(()->{
            while(true){
                log.info("this is t2 thread");
            }
        });
        t2.setPriority(8);
        t1.start();
        t2.start();
    }
}
