package com.learn.TypeOfLock.SynchronizedDemo;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

/**
 * autor:liman
 * createtime:2019/6/27
 * comment: synchronized的使用实例
 */
public class SyncDemo {

    private int i= 0;

    public synchronized void objectLock1(){
        for(int count=0;count<10;count++){
            i++;
        }

    }

    public synchronized void objectLock2(){
        synchronized (this){
            for(int count=0;count<10;count++){
                i++;
            }
        }
    }

    //synchronized修饰static方法，这个加的是类锁
    public synchronized static void classLock1(){

    }

    //synchronized中加的是class类锁
    public synchronized void classLock2(){
        synchronized (SyncDemo.class){

        }
    }

    public static void main(String[] args) {
        SyncDemo lockDemo01 = new SyncDemo();
        SyncDemo lockDemo02 = new SyncDemo();
        new Thread(()->{
            lockDemo01.objectLock1();
        }).start();

        new Thread(()->{
            lockDemo02.objectLock2();
        }).start();
    }

}
