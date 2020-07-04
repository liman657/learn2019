package com.learn.thread.update.phase01.notifyandwait;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * autor:liman
 * createtime:2020/6/16
 * comment:模拟自定义线程组
 */
@Slf4j
public class SelfThreadGroupService {

//    final static private Object LOCK = new Object();
    private final static int MAX_WROKERSIZE = 5;
    //没有什么实质作用，只是做一个大小的限制判断
    final static private LinkedList<Control> CONTROLS = new LinkedList<>();

    public static void main(String[] args) {
        List<Thread> allWorkerThreadList = new ArrayList<>();
        //单纯的启动所有线程，并将线程翻入workerList
        Arrays.asList("M1","M2","M3","M4","M5","M6","M7","M8","M9","M10").stream()
                .map(SelfThreadGroupService::createWorkerThread)
                .forEach(t->{
                    t.start();
                    allWorkerThreadList.add(t);
                });

        //通过workerList进行join，因为不能再start的时候进行join操作，否则会无法做到并行
        allWorkerThreadList.stream().forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                log.error("线程执行异常，异常信息为:{}",e);
            }
        });
        log.info("所有的线程执行完毕");
    }

    private static Thread createWorkerThread(String name){
        return new Thread(()->{
            synchronized (CONTROLS) {
                //如果大于我们限制的数字，则等待
                while (CONTROLS.size() > MAX_WROKERSIZE) {
                    try {
                        CONTROLS.wait();
                    } catch (InterruptedException e) {
                        log.error("线程执行异常，异常信息为:{}",e);
                    }
                }
                //没有超限，则先进入到我们的缓存中
                CONTROLS.addLast(new Control());
            }

            //模拟当前线程的执行
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //执行完成之后，从缓存中移出
            synchronized (CONTROLS) {
                Optional.of("The worker [" + Thread.currentThread().getName() + "] END capture data.")
                        .ifPresent(System.out::println);
                CONTROLS.removeFirst();
                CONTROLS.notifyAll();
            }
        },name);
    }

    private static class Control {
    }

}
