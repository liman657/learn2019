package com.learn.thread.update.phase01.selfThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * autor:liman
 * createtime:2020/6/22
 * comment:简单的线程池——加入拒绝策略
 */
@Slf4j
public class SimpleThreadPoolV2 {

    //初始化的线程池大小
    private final int size;
    private final static int DEFAULT_SIZE=10;//线程池的默认大小

    //任务队列的大小
    private final int queueSize;
    private final static int DEFAULT_TASK_QUEUE_SIZE = 2000;//任务队列的默认大小
    private static volatile int seq=0;
    private final static String THREAD_PREFIX="SIMPLE_THREAD_POOL-";
    private final static ThreadGroup GROUP = new ThreadGroup("SELF-POOL-THREADGROUP");

    //外部提交的任务线程
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();
    //线程池内部的活跃线程
    private final static List<WorkTask> THREAD_QUEUE= new ArrayList<>();

    private final DiscardPolicy discardPolicy;//拒绝策略
    //默认的拒绝策略
    private final static DiscardPolicy DEFAULT_DISCARD_POLICY = ()->{
        throw new DiscardException("discard this task.");
    };

    //外部的标示，线程池是否停止
    private volatile boolean isDestory = false;

    public SimpleThreadPoolV2() {
        this(DEFAULT_SIZE,DEFAULT_TASK_QUEUE_SIZE,DEFAULT_DISCARD_POLICY);
    }

    /**
     * 提供给外部的构造方法
     * @param size
     * @param queueSize
     * @param discardPolicy
     */
    public SimpleThreadPoolV2(int size,int queueSize,DiscardPolicy discardPolicy) {
        this.size = size;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
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
        //如果线程池已经被销毁，则直接不让提交任务
        if(isDestory){
            throw new IllegalStateException("the thread pool already destory");
        }

        synchronized (TASK_QUEUE){
            //判断数量，决定是否执行拒绝策略
            int currentTaskCount = TASK_QUEUE.size();
            log.info("目前的任务数量：{},规定的队列大小:{}",currentTaskCount,queueSize);
            if(currentTaskCount>queueSize){
                discardPolicy.discard();
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    /**
     * 拒绝策略
     */
    public static class DiscardException extends RuntimeException{
        public DiscardException(String message) {
            super(message);
        }
    }

    /**
     * 拒绝策略定义的接口
     */
    public interface DiscardPolicy{
        void discard() throws DiscardException;
    }

    /**
     * 关闭线程池
     */
    public void shutdown() throws InterruptedException {
        log.info("当前任务队列中任务的个数:{}",TASK_QUEUE.size());
        while(!TASK_QUEUE.isEmpty()){//如果任务队列不空，需要轮询，等待任务队列执行完成
            Thread.sleep(50);
        }

        int initVal = THREAD_QUEUE.size();
        while(initVal>0){
            for(WorkTask task:THREAD_QUEUE){
                if(task.getTaskState() == TaskState.BLOCKED){//如果是阻塞住的，才将其停止
                    task.interrupt();
                    task.close();//这里调用close的原因：如果run是在获取任务的时候，是无法响应中断的。
                    initVal--;
                }else{
                    Thread.sleep(10);
                }
            }
        }

        log.info("group account : {}",GROUP.activeCount());
        this.isDestory = true;
        log.info("Thread pool shutdown!");
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
            while(this.taskState!= TaskState.TERMINATE){//如果线程状态没有终止
                //从任务队列中取出任务，然后执行
                Runnable runnable;
                synchronized (TASK_QUEUE){
                    while(TASK_QUEUE.isEmpty()){//没有任务，当前task需要wait
                        try {
                            this.taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            //外部打断的时候，需要退出到while循环之外，不能抛出异常
                            break OUTER;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();
                }

                //获取任务的时候，不需要在synchronize中
//                runnable = TASK_QUEUE.removeFirst();
                if(null!=runnable){
                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                }
            }
        }

        public void close(){
            this.taskState = TaskState.TERMINATE;
        }

    }

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPoolV2 threadPoolV2 = new SimpleThreadPoolV2(5,50,SimpleThreadPoolV2.DEFAULT_DISCARD_POLICY);
//        SimpleThreadPoolV2 threadPoolV2 = new SimpleThreadPoolV2();
//        IntStream.rangeClosed(0,20)
//                .forEach(i->threadPoolV2.submit(()->{
//                    log.info("the runnable {} be serviced at {}",i,LocalDateTime.now().toString());
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    log.info("the runnable {} finished at {}",i,LocalDateTime.now().toString());
//                }));
        for(int i = 0;i<40;i++){
            threadPoolV2.submit(() -> {
                log.info("the runnable be serviced at {}",LocalDateTime.now().toString());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("the runnable finished at {}",LocalDateTime.now().toString());
            });
        }

        Thread.sleep(1000);
        threadPoolV2.shutdown();
    }
}
