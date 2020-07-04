package com.learn.thread.update.phase02.volatileLearn;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2020/6/29
 * comment:
 */
@Slf4j
public class VolatileFoo {

    final static int MAX = 5;
    static volatile int init_value = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            int localValue = init_value;
            while (localValue < MAX) {
                if (init_value != localValue) {//如果发现本地的localValue已经不等于initValue了，需要更新本地的localValue
                    log.info("the init_value is updated to {}", init_value);
                    localValue = init_value;
                }
            }
        }, "reader").start();

        new Thread(() -> {
            int localValue = init_value;
            while (localValue < MAX) {
                log.info("the init_value will be changed to {}", ++localValue);
                init_value = localValue;
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "updater").start();
    }

}
