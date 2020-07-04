package com.learn.thread.update.phase01.threadInit;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/6/6
 * comment:父线程的名称
 */
@Slf4j
public class ParentThreadName {

    public static void main(String[] args) {
        Runnable runnable = ()->{
            log.info("this is child thread，this parent is :{}",Thread.currentThread().getThreadGroup().getName());
        };
        Thread t = new Thread(runnable);
        t.start();

        log.info("main thread group is : {}",Thread.currentThread().getThreadGroup().getName());
    }

}
