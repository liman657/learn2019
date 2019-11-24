package com.learn.executorpoollearn;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2019/11/13
 * comment: 线程池中的复用实例（纯手动实现的线程池实例）
 */
@Slf4j
public class WorkerThreadPoolDemo {

    LinkedList<Task> taskList = new LinkedList<>();

    class Task{//任务类
        int id ;
        Task(int id){
            this.id = id;
            log.info("第{}个任务产生",id);
        }

        public void run(){
            log.info("第{}个任务正在执行",id);
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){
                log.error("异常消息，{}",e.fillInStackTrace());
            }
            log.info("第{}个任务执行完毕",id);
        }
    }

    class Worker extends Thread{//工人实体
        String name;
        Worker(String name){
            this.name = name;
        }

        public void run(){
            while(true){
                if(taskList.size()==0){
                    try {
                        synchronized (taskList){
                            log.info("worker {} 没有任务",name);
                            taskList.wait();
                        }
                    }catch (Exception e){
                        log.error("发生异常，异常信息为:{}",e.fillInStackTrace());
                    }
                }

                synchronized (taskList){
                    log.info("worker {} 得到任务",name);
                    taskList.removeFirst().run();
                }
            }
        }
    }

    void pool(){//产生工人的池
        ArrayList<Worker> workerlist=new ArrayList<Worker>();
        for(int i = 0;i<2;i++){
            Worker worker = new Worker("第"+(i+1)+"个工人");
            worker.start();
            workerlist.add(worker);
        }
    }

    class Factory extends Thread{//生产任务的线程，总共会生产10个任务
        public void run(){
            for(int i=0;i<10;i++){
                synchronized (taskList){
                    taskList.addLast(new Task(i+1));
                    taskList.notify();
                }

                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch (Exception e){
                    log.info("线程异常,异常信息为:{}",e.fillInStackTrace());
                }
            }
        }
    }

    public static void main(String[] args) {
        WorkerThreadPoolDemo myThreadPool = new WorkerThreadPoolDemo();
        myThreadPool.pool();
        WorkerThreadPoolDemo.Factory factory = myThreadPool.new Factory();
        factory.start();
    }

}
