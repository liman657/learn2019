package com.learn.start;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2019/6/19
 * comment:
 */
public class ThreadStatusDemo {

    public static void main(String[] args) {

        new Thread(()->{
           while(true){
               try{
                   TimeUnit.SECONDS.sleep(100);
               }catch (InterruptedException e){
                    e.printStackTrace();
               }
           }
        }).start();

        new Thread(()->{
            while(true){
                try {
                    ThreadStatusDemo.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
