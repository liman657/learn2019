package com.learn.threadFund.chapter01;

/**
 * autor:liman
 * comment:
 */
public class RandomMain {

    public static void main(String[] args) {
        try{
            RandomThread randomThread = new RandomThread();
            randomThread.setName("randomThread");
            randomThread.start();


            for(int i=0;i<10;i++){
                int time = (int) (Math.random()*1000);
                Thread.sleep(time);
                System.out.println("main thread = "+Thread.currentThread().getName()+System.currentTimeMillis());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
