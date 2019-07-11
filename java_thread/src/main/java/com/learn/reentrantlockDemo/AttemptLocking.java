package com.learn.reentrantlockDemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * autor:liman
 * createtime:2019/7/2
 * comment:
 */
public class AttemptLocking {

    private ReentrantLock lock = new ReentrantLock();

    public void untimed(){
        boolean captured = lock.tryLock();
        try{
            System.out.println("try lock : "+captured);
        }finally {
            if(captured){
                lock.unlock();
            }
        }
    }

    public void timed(){
        boolean captured = false;
        try {
            lock.tryLock(2,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try{
            System.out.println("try lock 2 seconds "+captured);
        }finally {
            if(captured)
                lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final AttemptLocking attemptLocking = new AttemptLocking();
        attemptLocking.untimed();
        attemptLocking.timed();

        new Thread(){
            {
                setDaemon(true);
            }

            @Override
            public void run() {
                attemptLocking.lock.lock();
                System.out.println("acquired");
            }
        }.start();
        Thread.sleep(100);
        attemptLocking.untimed();
        attemptLocking.timed();
    }

}
