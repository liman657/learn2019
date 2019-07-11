package com.learn.cyclicbarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * author:liman
 * createtime:2019/7/11
 */
public class CyclicBarrierDemo {

    public static class Soldier implements Runnable{

        private String soldier;
        private final CyclicBarrier cyclicBarrier;

        Soldier(CyclicBarrier cyclicBarrier,String soldier){
            this.cyclicBarrier = cyclicBarrier;
            this.soldier = soldier;
        }

        @Override
        public void run() {
            try {
                //所有线程开始的起点
                cyclicBarrier.await();
                doWork();
                //等待所有士兵完成任务
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        public void doWork(){
            try {
                Thread.sleep(Math.abs(new Random().nextInt()%1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldier+":任务完成");
        }
    }

    public static class BarrierRun implements Runnable{
        boolean flag;
        int N;

        public BarrierRun(boolean flag,int N){
            this.flag = flag;
            this.N = N;
        }

        @Override
        public void run() {
            if(flag){
                System.out.println("司令：【士兵"+N+"个，任务完毕！】");
            }else{
                System.out.println("司令：【士兵"+N+"个，集合完毕！】");
                flag=true;
            }
        }
    }

    public static void main(String[] args) {
        final int N = 10;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;
        //初始化一个CyclicBarrier，在计数器达到指标的时候，执行BarrierRun中的run方法
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N,new BarrierRun(flag,N));
        System.out.println("集合队伍");
        for(int i=0;i<N;i++){
            System.out.println("士兵"+i+"报道!");
            allSoldier[i] = new Thread(new Soldier(cyclicBarrier,"士兵"+i));
            allSoldier[i].start();
        }
    }

}
