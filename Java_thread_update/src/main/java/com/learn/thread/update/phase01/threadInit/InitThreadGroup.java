package com.learn.thread.update.phase01.threadInit;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * autor:liman
 * createtime:2020/6/6
 * comment: ThreadGroup的实例
 */
@Slf4j
public class InitThreadGroup {

    public static void main(String[] args) {
        Runnable r = ()->{
            try {
//                Thread.sleep(500l);
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.info("this is child thread");
        };

        ThreadGroup threadGroup = new ThreadGroup("slef thread group");
        ThreadGroup mainThreadGroup = Thread.currentThread().getThreadGroup();
//        Thread thread = new Thread(threadGroup,r,"childThread");
        Thread thread = new Thread(r,"self thread");
        thread.start();
//        log.info("mainThread belongs thread group : {}",mainThreadGroup.getName());
//        log.info("childThread belongs thread group : {}",threadGroup.getName());
//        log.info("two thread group is same : {}",mainThreadGroup==thread.getThreadGroup());
        log.info("active account : {}",mainThreadGroup.activeCount());
        Thread[] threads = new Thread[mainThreadGroup.activeCount()];
        //将mainThreadGroup中的线程遍历，放到threads中
        mainThreadGroup.enumerate(threads);
//        Arrays.stream(threads).forEach(t->log.info("thread name : {}",t.getName()));
        Arrays.stream(threads).forEach(System.out::println);
//        Arrays.stream(threads).map(t->t.getName()).collect(Collectors.joining(", "));
    }

}
