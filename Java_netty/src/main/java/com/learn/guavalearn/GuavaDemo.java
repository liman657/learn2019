package com.learn.guavalearn;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * autor:liman
 * createtime:2019/11/13
 * comment:guava的异步调用实例
 *
 * Guava 异步回调和Java的FutureTask异步回调，其本质的不同在于
 * 1、Guava是非阻塞的异步回调，调用线程是不阻塞的，可以继续执行自己的业务逻辑
 * 2、FutureTask 是阻塞的异步回调，调用线程是阻塞的，在获取异步结果的过程中，一直阻塞，等待异步线程返回结果。
 */
@Slf4j
public class GuavaDemo {

    public static final int SLEEP_GAP = 500;

    public static String getCurrentThreadName(){
        return Thread.currentThread().getName();
    }

    /**
     * 烧水的Callable接口
     */
    static class HotWaterJobGuava implements Callable<Boolean>{

        @Override
        public Boolean call() throws Exception {
            try {
                log.info("guava 洗好水壶");
                log.info("guava 放入凉水");
                log.info("guava 放在火上");
                Thread.sleep(SLEEP_GAP);
                log.info("guava 水开了");
            }catch (Exception e){
                log.error("guava 异常中断");
                return false;
            }
            log.info("{},运行结束",getCurrentThreadName());
            return true;
        }
    }

    /**
     * 洗杯子的Callable接口
     */
    static class WashCupJobGuava implements Callable<Boolean>{

        @Override
        public Boolean call() throws Exception {
            try{
                log.info("guava 清洗茶壶");
                log.info("guava 清洗茶杯");
                log.info("guava 拿茶叶");
                Thread.sleep(SLEEP_GAP);
                log.info("guava 洗完了");
            }catch (Exception e){
                log.info("guava 清洗工作发生异常");
                return false;
            }
            log.info("guava 清洗完成");
            return true;
        }
    }

    static class MainJob implements Runnable{

        boolean waterOk = false;
        boolean cupOk = false;

        int gap = SLEEP_GAP/10;

        @Override
        public void run() {
            while(true){
                try{
                    Thread.sleep(gap);
                    log.info("读书中......");
                }catch (Exception e){
                    log.error("异常被中断......");
                }

                if(waterOk && cupOk){
                    drinkTea(waterOk,cupOk);
                }
            }
        }

        private void drinkTea(boolean waterOk, boolean cupOk) {
            if(waterOk && cupOk){
                log.info("可以开始喝茶了");
                this.waterOk = false;
                this.gap = SLEEP_GAP * 10;
            }else if(!waterOk){
                log.warn("烧水失败，无法喝茶");
            }else if(!cupOk){
                log.warn("清洗茶具失败，无法喝茶");
            }
        }
    }

    public static void main(String[] args) {
        //创建一个主线程
        MainJob mainJob = new MainJob();

        Thread mainThread = new Thread(mainJob);
        mainThread.setName("主要的喝水线程");
        mainThread.start();//到这里，主线程就完成了，后面的操作都是非阻塞的

        //烧水的业务逻辑
        Callable<Boolean> hotWaterJob = new HotWaterJobGuava();
        Callable<Boolean> washCupJob = new WashCupJobGuava();

        ExecutorService jPool = Executors.newFixedThreadPool(10);
        ListeningExecutorService gPool = MoreExecutors.listeningDecorator(jPool);

        //提交烧水的业务逻辑
        ListenableFuture<Boolean> hotWaterFuture = gPool.submit(hotWaterJob);
        //绑定异步回调
        Futures.addCallback(hotWaterFuture,new FutureCallback<Boolean>(){

            @Override
            public void onSuccess(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    mainJob.waterOk = true;
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.error("出现异常,{}",throwable.fillInStackTrace());
            }
        });

        ListenableFuture<Boolean> washCupFuture = gPool.submit(washCupJob);
        Futures.addCallback(washCupFuture, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    mainJob.cupOk = true;
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.error("出现异常,{}",throwable.fillInStackTrace());
            }
        });
    }
}