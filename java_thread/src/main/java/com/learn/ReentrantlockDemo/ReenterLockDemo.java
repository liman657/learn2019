package com.learn.ReentrantlockDemo;

import java.util.concurrent.locks.ReentrantLock;

/**
 * autor:liman
 * createtime:2019/7/4
 * comment:
 * 一段简单的重入锁实例
 */
public class ReenterLockDemo implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();

    public static int i = 0;

    /**
     * lock.lock()和lock.unlock()之间的语句构成了类似同步代码块。
     * 何时加锁，何时释放锁，完全手动配置，更加灵活。
     */
    @Override
    public void run() {
        for (int j= 0; j < 100000; j++) {
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterLockDemo reenterLockDemo = new ReenterLockDemo();
        Thread t1 = new Thread(reenterLockDemo);
        Thread t2 = new Thread(reenterLockDemo);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
