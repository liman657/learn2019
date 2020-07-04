package com.learn.thread.update.phase01.selfThreadPool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * autor:liman
 * createtime:2020/6/22
 * comment:简单的线程池
 */
public class SimpleThreadPoolV1 {

    private final int size;
    private final static int DEFAULT_SIZE=10;
    private static volatile int seq=0;
    private final static String THREAD_PREFIX="SIMPLE_THREAD_POOL-";
    private final static ThreadGroup GROUP = new ThreadGroup("SELF-POOL-THREADGROUP");

    //外部提交的任务线程
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
    //线程池内部的活跃线程
    private final static List<WorkTask> THREAD_QUEUE= new ArrayList<>();

    public SimpleThreadPoolV1() {
        this(DEFAULT_SIZE);
    }

    public SimpleThreadPoolV1(int size) {
        this.size = size;
        init();
    }

    private void init() {
        for(int i = 0;i<size;i++){
            createWorkTask();
        }
    }

    private void createWorkTask(){
        WorkTask task = new WorkTask(GROUP,THREAD_PREFIX+(seq++));
        task.start();
        THREAD_QUEUE.add(task);
    }

    /**
     * 对外提供的提交任务方法
     * @param runnable
     */
    public void submit(Runnable runnable){
        synchronized (TASK_QUEUE){
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    /**
     * 表示当前WorkState的状态
     */
    private enum TaskState{
        FREE,RUNNING,BLOCKED,TERMINATE;
    }

    /**
     * 对原有的Thread进行封装
     * 这里的线程执行完成之后，不能挂掉，需要继续执行后续队列中的任务
     */
    private static class WorkTask extends Thread{

        private volatile TaskState taskState = TaskState.FREE;

        public WorkTask(ThreadGroup threadGroup,String name){
            super(threadGroup,name);
        }

        public TaskState getTaskState(){
            return this.taskState;
        }

        @Override
        public void run() {
            OUTER:
            while(this.taskState!=TaskState.TERMINATE){//如果线程状态没有终止
                //从任务队列中取出任务，然后执行
                Runnable runnable;
                synchronized (TASK_QUEUE){
                    while(TASK_QUEUE.isEmpty()){//没有任务，当前task需要wait
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            //外部打断的时候，需要退出到while循环之外，不能抛出异常
                            break OUTER;
                        }
                    }

                    runnable = TASK_QUEUE.removeFirst();
                    if(null!=runnable){
                        taskState = TaskState.RUNNING;
                        runnable.run();
                        taskState = TaskState.FREE;
                    }
                }
            }
        }

        public void close(){
            this.taskState = TaskState.TERMINATE;
        }

    }
}
