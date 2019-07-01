package com.learn.notifyAndWait;

/**
 * autor:liman
 * createtime:2019/7/1
 * comment:
 */
public class NotifyDemo implements Runnable {

    private Object lock;

    public NotifyDemo(Object lock){
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            System.out.println("begin notify() thread name = "+Thread.currentThread().getName());
            // [STEP-2] NotifyDemo线程唤醒其他挂起的线程(WaitDemo线程)。但是此时它并不立即释放锁。
            lock.notify();
            try {
                System.out.println("sleep 3s");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end notify() thread name = "+Thread.currentThread().getName());
            // [STEP-3] 释放锁。
        }
    }
}
