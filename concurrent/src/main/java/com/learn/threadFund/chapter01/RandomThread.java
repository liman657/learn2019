package com.learn.threadFund.chapter01;

/**
 * autor:liman
 * comment:
 */
public class RandomThread extends Thread{

    @Override
    public void run() {
        try{
            for(int i = 0 ;i<10;i++){
                int time = (int) (Math.random()*1000);
                Thread.sleep(time);
                System.out.println("run = "+Thread.currentThread().getName()+System.currentTimeMillis());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
