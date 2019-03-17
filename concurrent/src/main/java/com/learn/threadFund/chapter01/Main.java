package com.learn.threadFund.chapter01;

/**
 * autor:liman
 * comment:
 */
public class Main {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
//        myThread.run();//直接调用run方法，会并不会启动线程
        myThread.start();
        System.out.println("线程运行结束");
    }

}
