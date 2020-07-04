package com.learn.thread.update.phase01.threadgroup;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * autor:liman
 * createtime:2020/6/21
 * comment:线程组的复制
 */
@Slf4j
public class ThreadGroupDulip {

    public static void main(String[] args) {
        log.info("main线程组的名称为:{}",Thread.currentThread().getThreadGroup().getName());

        //父线程默认为main 线程组
        ThreadGroup threadGroupOne = new ThreadGroup("thread group one");
        Thread threadOne = new Thread(threadGroupOne,()->{
            try {
                Thread currentThread = Thread.currentThread();
                ThreadGroup currentGroup = currentThread.getThreadGroup();
                ThreadGroup parentGroup = currentThread.getThreadGroup().getParent();
//                int currentActiveAcount = currentGroup.activeCount();
//                int parentActiveAcount = parentGroup.activeCount();
//                log.info("当前线程：{}，所属线程组：{}，父线程组：{}",currentThread.getName(),currentGroup.getName(),parentGroup.getName());
//                log.info("当前线程组的活动线程数量：{}，父线程组的活动线程数量：{}",currentActiveAcount,parentActiveAcount);
                Thread.sleep(10_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"threadOne");//创建线程的时候，显示指定线程组
        threadOne.start();

        ThreadGroup threadGroupTwo = new ThreadGroup(threadGroupOne,"thread group two");
        Thread threadTwo = new Thread(threadGroupTwo,()->{
            try {
                Thread currentThread = Thread.currentThread();
                ThreadGroup currentGroup = currentThread.getThreadGroup();
                ThreadGroup parentGroup = currentThread.getThreadGroup().getParent();
//                int currentActiveAcount = currentGroup.activeCount();
//                int parentActiveAcount = parentGroup.activeCount();
//                log.info("当前线程：{}，所属线程组：{}，父线程组：{}",currentThread.getName(),currentGroup.getName(),parentGroup.getName());
//                log.info("当前线程组的活动线程数量：{}，父线程组的活动线程数量：{}",currentActiveAcount,parentActiveAcount);
                Thread.sleep(10_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"threadTwo");
        threadTwo.start();

//        getActiveCount(threadGroupOne);
//        getActiveCount(threadGroupTwo);
//        getActiveCount(Thread.currentThread().getThreadGroup());
        Thread[] targetThreadList = new Thread[threadGroupOne.activeCount()];

//        duplicate(threadGroupOne,targetThreadList);
        ThreadGroup[] threadGroups = new ThreadGroup[Thread.currentThread().getThreadGroup().activeGroupCount()];
        duplicate(Thread.currentThread().getThreadGroup(),threadGroups);
    }

//    /**
//     * 获取活动的线程数
//     */
//    public static void getActiveCount(ThreadGroup threadGroup){
//        String threadGroupName = threadGroup.getName();
//        log.info("{}中活跃线程数为：{}",threadGroupName,threadGroup.activeCount());
//    }

    public static void duplicate(ThreadGroup originalThreadGroup,Thread[] threadList){
        String orginalThreadGroupName = originalThreadGroup.getName();
        originalThreadGroup.enumerate(threadList,false);//无递归拷贝
        log.info("线程组：{}，无递归拷贝后的线程名称为",orginalThreadGroupName);
        Arrays.asList(threadList).forEach(t->{if(t!=null) log.info("{}",t.getName());});

        originalThreadGroup.enumerate(threadList,true);
        log.info("线程组：{}，递归拷贝后的线程名称为",orginalThreadGroupName);
        Arrays.asList(threadList).forEach(t->{if(t!=null) log.info("{}",t.getName());});
    }


    public static void duplicate(ThreadGroup originalThreadGroup,ThreadGroup[] threadGroups){
        String orginalThreadGroupName = originalThreadGroup.getName();
        originalThreadGroup.enumerate(threadGroups,false);//无递归拷贝
        log.info("线程组：{}，无递归拷贝后的线程组名称为",orginalThreadGroupName);
        Arrays.asList(threadGroups).forEach(t->{if(t!=null) log.info("{}",t.getName());});

        originalThreadGroup.enumerate(threadGroups,true);
        log.info("线程组：{}，递归拷贝后的线程组名称为",orginalThreadGroupName);
        Arrays.asList(threadGroups).forEach(t->{if(t!=null) log.info("{}",t.getName());});
        originalThreadGroup.interrupt();
    }
}
