package com.learn.thread.update.phase01.bookThreadPool;

/**
 * autor:liman
 * createtime:2020/6/24
 * comment:线程池接口，定义了一些线程池的基本操作
 */
public interface ThreadPool {

    //接受并提交runnable任务
    void execute(Runnable runnable);

    //关闭并销毁线程池
    void shutdown();

    //返回线程池的初始化线程个数
    int getInitSize();

    //返回线程池的最大线程个数
    int getMaxSize();

    //返回核心线程数量
    int getCoreSize();

    //返回当前线程池任务数量
    int getQueueSize();

    //获取线程池中当前活跃线程数量
    int getActiveCount();

    //判断线程池是否已经被销毁
    boolean isShutDown();

}
