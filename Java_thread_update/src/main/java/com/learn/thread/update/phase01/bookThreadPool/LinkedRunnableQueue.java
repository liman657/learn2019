package com.learn.thread.update.phase01.bookThreadPool;

import java.util.LinkedList;

/**
 * autor:liman
 * createtime:2020/6/24
 * comment:
 */
public class LinkedRunnableQueue implements RunnableQueue {

    //任务队列的最大容量
    private final int limit;
    //拒绝策略
    private final DenyPolicy denyPolicy;
    //存放任务的队列
    private final LinkedList<Runnable> runnableList = new LinkedList<>();

    private final ThreadPool threadPool;

    public LinkedRunnableQueue(int limit, DenyPolicy denyPolicy, ThreadPool threadPool) {
        this.limit = limit;
        this.denyPolicy = denyPolicy;
        this.threadPool = threadPool;
    }

    @Override
    public void offer(Runnable runnable) {
        synchronized (runnable){
            if(runnableList.size() >= limit){
                //无法容纳新的任务时，执行拒绝策略
                denyPolicy.reject(runnable,threadPool);
            }else{
                runnableList.addLast(runnable);
                runnableList.notifyAll();
            }
        }
    }

    @Override
    public Runnable take() throws InterruptedException {
        synchronized (runnableList){
            while(runnableList.isEmpty()){
                try{
                    //如果任务队列中没有可执行的任务，则当前线程将会挂起，进入到RunnableList关联的等待队列中。
                    runnableList.wait();
                }catch (InterruptedException e){
                    //被中断的时候，抛出异常
                    throw e;
                }
            }
            //从队列头部取出一个任务
            return runnableList.removeFirst();
        }
    }

    @Override
    public int size() {
        synchronized (runnableList){
            return runnableList.size();
        }
    }
}
