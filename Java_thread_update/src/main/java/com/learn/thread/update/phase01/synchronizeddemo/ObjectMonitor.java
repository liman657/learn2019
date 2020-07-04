package com.learn.thread.update.phase01.synchronizeddemo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2020/6/11
 * comment:this monitor示例
 */
@Slf4j
public class ObjectMonitor {

    public synchronized void method01() {
        log.info(Thread.currentThread().getName() + " enter to method01");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public synchronized void method02() {
//        log.info(Thread.currentThread().getName()+" enter to method02");
//        try {
//            TimeUnit.SECONDS.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        synchronized (this){
//            log.info(Thread.currentThread().getName() + " enter to method02");
//            try {
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public void method02(){
        synchronized (this){
            log.info(Thread.currentThread().getName() + " enter to method02");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ObjectMonitor thisMonitor = new ObjectMonitor();
        new Thread(thisMonitor::method01,"T1").start();
        new Thread(thisMonitor::method02,"T2").start();
    }

}
