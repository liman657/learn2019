package com.learn.thread.update.phase01.selfLock;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * autor:liman
 * createtime:2020/6/19
 * comment:
 */
@Slf4j
public class BooleanLockV2 implements Lock {

    //如果为true，表示已经被占用了，如果为false，则表示该锁空闲
    private boolean initValue;

    private Collection<Thread> blockedThreadCollections = new ArrayList<>();

    private Thread currentThread;

    public BooleanLockV2() {
        this.initValue = false;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        while(initValue){
            //没有抢到锁，进入到等待队列
            blockedThreadCollections.add(Thread.currentThread());
            this.wait();//在booleanLock对象上等待
        }

        //抢到锁了
        initValue = true;
        //从等待队列中删除
        blockedThreadCollections.remove(Thread.currentThread());
        //还需要记录持有这个锁的线程
        this.currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void lock(long mills) throws InterruptedException, TimeOutException {
        if(mills<0){
            lock();
        }

        long hasRemaining = mills;
        long endTime = System.currentTimeMillis()+mills;

        while(initValue){
            if(hasRemaining<0){//到了该被唤醒的时候
                throw new TimeOutException("Time out");
            }
            blockedThreadCollections.add(Thread.currentThread());
            this.wait(mills);
            hasRemaining = endTime - System.currentTimeMillis();
        }
        //如果被正常的notify唤醒了。
        this.initValue=true;
        this.currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void unlock() {
        if(Thread.currentThread() == currentThread) {//只有与当前线程匹配的线程，才能释放
            //释放锁的第一步，将标志位置为false
            this.initValue = false;
            log.info("Thread:{},release the lock", Thread.currentThread().getName());
            this.notifyAll();
        }

    }

    /**
     * 返回在等待的线程集合
     * @return
     */
    @Override
    public Collection<Thread> getBlockedThread() {
//        return blockedThreadCollections;//肯定不能这样返回，因为这样会被修改
        return Collections.unmodifiableCollection(blockedThreadCollections);
    }

    @Override
    public int getBlockThreadSize() {
        return blockedThreadCollections.size();
    }
}
