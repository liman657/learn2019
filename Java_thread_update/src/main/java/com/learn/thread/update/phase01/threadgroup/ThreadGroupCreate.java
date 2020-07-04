package com.learn.thread.update.phase01.threadgroup;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/6/21
 * comment:线程组的创建
 */
@Slf4j
public class ThreadGroupCreate {

    public static void main(String[] args) {

        log.info("main线程组的名称为:{}",Thread.currentThread().getThreadGroup().getName());

        //父线程默认为main 线程组
        ThreadGroup threadGroupOne = new ThreadGroup("thread group one");
        Thread threadOne = new Thread(threadGroupOne,()->{
            try {
                Thread currentThread = Thread.currentThread();
                ThreadGroup currentGroup = currentThread.getThreadGroup();
                ThreadGroup parentGroup = currentThread.getThreadGroup().getParent();
                int currentActiveAcount = currentGroup.activeCount();
                int parentActiveAcount = parentGroup.activeCount();
                log.info("当前线程：{}，所属线程组：{}，父线程组：{}",currentThread.getName(),currentGroup.getName(),parentGroup.getName());
                log.info("当前线程组的活动线程数量：{}，父线程组的活动线程数量：{}",currentActiveAcount,parentActiveAcount);
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
                int currentActiveAcount = currentGroup.activeCount();
                int parentActiveAcount = parentGroup.activeCount();
                log.info("当前线程：{}，所属线程组：{}，父线程组：{}",currentThread.getName(),currentGroup.getName(),parentGroup.getName());
                log.info("当前线程组的活动线程数量：{}，父线程组的活动线程数量：{}",currentActiveAcount,parentActiveAcount);
                Thread.sleep(10_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"threadTwo");
        threadTwo.start();
    }
}