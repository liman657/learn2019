package com.learn.thread.update.synchronizeddemo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2020/6/11
 * comment:class monitor实例
 */
@Slf4j
public class ClassMonitor {

    public static synchronized void method01(){
        log.info(Thread.currentThread().getName()+" enter to method01");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static synchronized void method02(){
//            log.info(Thread.currentThread().getName()+ " enter to method02");
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//    }

    public static synchronized void method02(){
        synchronized (ClassMonitor.class) {
            log.info(Thread.currentThread().getName() + " enter to method02");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(ClassMonitor::method01,"T1").start();
        new Thread(ClassMonitor::method02,"T2").start();
    }

}
