package com.learn.thread.update.phase01.selfThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

/**
 * autor:liman
 * createtime:2020/6/23
 * comment:测试线程池
 */
@Slf4j
public class ThreadPoolTest {

    public static void main(String[] args) {
        SimpleThreadPoolV1 simpleThreadPoolV1 = new SimpleThreadPoolV1();
        IntStream.rangeClosed(0, 20)
                .forEach(t -> simpleThreadPoolV1.submit(() -> {
                    log.info("the runnable {} be serviced by {}", t, Thread.currentThread());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        log.error("运行出现异常");
                    }
                }));
    }

}
