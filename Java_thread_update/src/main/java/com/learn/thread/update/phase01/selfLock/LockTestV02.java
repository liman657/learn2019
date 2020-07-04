package com.learn.thread.update.phase01.selfLock;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2020/6/19
 * comment:自己实现的锁，初始版本的测试
 */
@Slf4j
public class LockTestV02 {

    public static void main(String[] args) {

        final BooleanLockV2 booleanLock = new BooleanLockV2();
        Stream.of("T1","T2","T3","T4","T5")
                .forEach(name->new Thread(()->{
                    try {
                        booleanLock.lock(12000);
                        log.info("Thread:{} get the lock",Thread.currentThread().getName());
                        work();
                    } catch (InterruptedException e) {
                        log.error("线程运行出现异常，异常信息为:{}",e);
                    }catch (Lock.TimeOutException e){
                        log.error("Thread:{},线程等待超时",Thread.currentThread().getName());
                    } finally {
                        booleanLock.unlock();
                    }
                },name).start());

        booleanLock.unlock();//如果不做处理，这里可以立即释放，导致所有的线程并不能串行化运行
    }

    public static void work() throws InterruptedException {
        log.info("Thread:{} is working...",Thread.currentThread().getName());
        //模拟业务处理
        Thread.sleep(10000);
    }

}
