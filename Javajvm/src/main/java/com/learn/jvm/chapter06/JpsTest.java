package com.learn.jvm.chapter06;

/**
 * autor:liman
 * createtime:2019/12/9
 * comment:
 */
public class JpsTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new JstackThreadDemo();
        thread.start();
    }
}

class JstackThreadDemo extends Thread{
    @Override
    public void run() {
        while(true){
            System.out.println("stack thread demo ");
        }
    }
}