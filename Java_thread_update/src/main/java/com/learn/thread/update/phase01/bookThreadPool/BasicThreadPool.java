package com.learn.thread.update.phase01.bookThreadPool;

import java.util.Queue;

/**
 * autor:liman
 * createtime:2020/6/24
 * comment:
 */
public class BasicThreadPool extends Thread implements ThreadPool {

    private final int initSize=0;

    private final int maxSize=10;

    private final int coreSize=10;

    private int activeCount;

    private final ThreadFactory threadFactory;

    private final RunnableQueue runnableQueue;

    private volatile boolean isShutdown=false;

    private final Queue<InternalTask> queueTaskQueue;

    public BasicThreadPool(ThreadFactory threadFactory,RunnableQueue runnableQueue,Queue<InternalTask> queueTaskQueue){
        this.threadFactory = threadFactory;
        this.runnableQueue = runnableQueue;
        this.queueTaskQueue = queueTaskQueue;
    }

    @Override
    public void execute(Runnable runnable) {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public int getInitSize() {
        return 0;
    }

    @Override
    public int getMaxSize() {
        return 0;
    }

    @Override
    public int getCoreSize() {
        return 0;
    }

    @Override
    public int getQueueSize() {
        return 0;
    }

    @Override
    public int getActiveCount() {
        return 0;
    }

    @Override
    public boolean isShutDown() {
        return false;
    }
}
