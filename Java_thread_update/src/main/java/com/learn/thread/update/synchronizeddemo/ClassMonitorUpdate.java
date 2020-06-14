package com.learn.thread.update.synchronizeddemo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2020/6/11
 * comment:class monitor实例
 */
@Slf4j
public class ClassMonitorUpdate {

    static {
        synchronized (ClassMonitorUpdate.class){
            try {
                log.info("static block thread name : "+Thread.currentThread().getName());
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

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
        synchronized (ClassMonitorUpdate.class) {
            log.info(Thread.currentThread().getName() + " enter to method02");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void method03(){
//        synchronized (ClassMonitorUpdate.class) {
            log.info(Thread.currentThread().getName() + " enter to method03");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        }
    }

    public static void main(String[] args) {
//        new Thread(ClassMonitorUpdate::method01,"T1").start();
//        new Thread(ClassMonitorUpdate::method02,"T2").start();
//        new Thread(ClassMonitorUpdate::method03,"T3").start();
    }

}
