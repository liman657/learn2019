package com.learn.thread.update.phase01.bookThreadPool;

/**
 * autor:liman
 * createtime:2020/6/24
 * comment:
 * InternalTask是Runnable的一个实现，主要用于线程池内部，该类会用到RunnableQueue,
 * 然后不断的从queue中取出某个Runnable,并运行对应的run方法。
 */
public class InternalTask implements Runnable {

    private final RunnableQueue runnableQueue;

    private volatile boolean running = true;

    public InternalTask(RunnableQueue runnableQueue) {
        this.runnableQueue = runnableQueue;
    }

    @Override
    public void run() {
        //如果当前任务为running状态并且没有中断，则其将不断地从queue中获取Runnable,然后执行run方法
        while(running&&!Thread.currentThread().isInterrupted()){
            try{
                Runnable task = runnableQueue.take();
                task.run();
            }catch (Exception e){
                running = false;
                break;
            }
        }
    }

    //停止当前任务，主要会在线程池的shutdown方法中使用
    public void stop(){
        this.running = false;
    }
}
