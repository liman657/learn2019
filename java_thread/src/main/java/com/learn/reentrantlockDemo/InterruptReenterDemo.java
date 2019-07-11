package com.learn.reentrantlockDemo;

import java.util.concurrent.locks.ReentrantLock;

/**
 * autor:liman
 * createtime:2019/7/4
 * comment:
 * 重入锁的中断实例
 */
public class InterruptReenterDemo implements Runnable{

    public static ReentrantLock lock01 = new ReentrantLock();
    public static ReentrantLock lock02 = new ReentrantLock();

    private int lock;

    public InterruptReenterDemo(int lock){
        this.lock = lock;
    }


    @Override
    public void run() {
        try{
            if(lock==1){
                lock01.lockInterruptibly();
                Thread.sleep(500);
                lock02.lockInterruptibly();
            }else{
                lock02.lockInterruptibly();
                Thread.sleep(500);
                lock01.lockInterruptibly();
            }
        }catch (Exception e){

        }finally {
            //isHeldByCurrentThread判断当前线程是否持有这个锁，如果持有则返回true，如果不是则返回false
            if(lock01.isHeldByCurrentThread()){
                lock01.unlock();
            }
            if(lock02.isHeldByCurrentThread()){
                lock02.unlock();
            }
            System.out.println(Thread.currentThread().getId()+":线程退出");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        InterruptReenterDemo interruptReenterDemo01 = new InterruptReenterDemo(1);
        InterruptReenterDemo interruptReenterDemo02 = new InterruptReenterDemo(2);
        Thread thread01 = new Thread(interruptReenterDemo01);
        Thread thread02 = new Thread(interruptReenterDemo02);
        thread01.start();
        thread02.start();
//        Thread.sleep(1000);
        thread02.interrupt();
        System.out.println(thread02.isInterrupted());
    }
}
