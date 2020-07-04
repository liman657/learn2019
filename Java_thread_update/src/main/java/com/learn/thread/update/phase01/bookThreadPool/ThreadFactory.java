package com.learn.thread.update.phase01.bookThreadPool;

/**
 * autor:liman
 * createtime:2020/6/24
 * comment:创建线程的工厂
 */
@FunctionalInterface
public interface ThreadFactory {
    Thread createThread(Runnable runnable);
}
