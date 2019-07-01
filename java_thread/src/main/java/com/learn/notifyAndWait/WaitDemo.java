package com.learn.notifyAndWait;

/**
 * autor:liman
 * createtime:2019/7/1
 * comment:
 */
public class WaitDemo implements Runnable{

    private Object lock;

    public WaitDemo(Object lock){
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock){
            System.out.println("Begin wait() thread name = "+Thread.currentThread().getName());
            try {
                // [STEP-1] WaitDemo线程立刻释放获得的对象锁lock，并放弃CPU，进入等待队列。
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // [STEP-4] WaitDemo有机会获得另一个线程释放的锁，并从等待的地方起开始执行。
            System.out.println("End wait() thread name = "+Thread.currentThread().getName());
        }
    }
}