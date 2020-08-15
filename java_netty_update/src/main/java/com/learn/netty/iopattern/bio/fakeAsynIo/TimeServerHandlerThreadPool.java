package com.learn.netty.iopattern.bio.fakeAsynIo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2020/8/11
 * comment:时间服务器的线程池
 */
@Slf4j
public class TimeServerHandlerThreadPool {

    private ExecutorService executorService;

    public TimeServerHandlerThreadPool(int maxPoolSize, int queueSize) {
        executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS
                , new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task){
        executorService.submit(task);
    }

}
