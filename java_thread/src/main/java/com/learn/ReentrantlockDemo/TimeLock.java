package com.learn.ReentrantlockDemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * autor:liman
 * createtime:2019/7/4
 * comment:
 * 锁申请等待限时
 */
public class TimeLock implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                Thread.sleep(6000);
            } else {
                System.out.println(Thread.currentThread().getName()+" get lock failed");
            }
        } catch (Exception e) {

        } finally {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        TimeLock timeLock = new TimeLock();
        Thread t1 = new Thread(timeLock,
                "thread01");
        Thread t2 = new Thread(timeLock,"thread02");
        t1.start();
        t2.start();
    }
}
