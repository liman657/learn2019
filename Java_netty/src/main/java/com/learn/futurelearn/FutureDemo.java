package com.learn.futurelearn;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * autor:liman
 * createtime:2019/11/13
 * comment: future的实例
 */
@Slf4j
public class FutureDemo {

    public static final int SLEEP_GAP = 500;
    public static String getCurrentThreadName(){
        return Thread.currentThread().getName();
    }

    static class HotWaterJob implements Callable<Boolean> {

        public Boolean call() throws Exception {
            try {
                log.info("洗好水壶");
                log.info("放入凉水");
                log.info("放在火上");
                Thread.sleep(SLEEP_GAP);
                log.info("水开了");
            }catch (Exception e){
                log.error("异常中断");
                return false;
            }
            log.info("{},运行结束",getCurrentThreadName());
            return true;
        }
    }

    static class WashCupJob implements Callable<Boolean>{

        public Boolean call() throws Exception {
            try{
                log.info("清洗茶壶");
                log.info("清洗茶杯");
                log.info("拿茶叶");
                Thread.sleep(SLEEP_GAP);
                log.info("洗完了");
            }catch (Exception e){
                log.info("清洗工作发生异常");
                return false;
            }
            log.info("清洗完成");
            return true;
        }
    }

    public static void drinkTea(boolean waterOk,boolean cupOk){
        if(waterOk && cupOk){
            log.info("可以泡茶喝了");
        }else if(!waterOk){
            log.info("烧水失败，没有茶喝了");
        }else if(!cupOk){
            log.info("杯子碎了，无法喝茶了");
        }
    }

    public static void main(String[] args) {
        Callable<Boolean> hotWaterJob = new HotWaterJob();
        Callable<Boolean> washCupJob = new WashCupJob();

        FutureTask<Boolean> hotWaterFuture = new FutureTask<Boolean>(hotWaterJob);
        FutureTask<Boolean> washCupFuture = new FutureTask<Boolean>(washCupJob);

        Thread hotWaterThread = new Thread(hotWaterFuture,"** 热水的线程");
        Thread washCupThread = new Thread(washCupFuture,"$$ 清洗茶具的线程");

        hotWaterThread.start();
        washCupThread.start();

        Thread.currentThread().setName("主线程");
        try{
            //这里似乎和join的效果差不多。
            boolean waterOk = hotWaterFuture.get();
            boolean cupOk = washCupFuture.get();
            drinkTea(waterOk,cupOk);
        }catch (Exception e){
            log.error("出现异常，异常信息为:{}",e.fillInStackTrace());
        }

        log.info("整个过程结束");
    }

}
