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
public class BooleanLock implements Lock {

    //如果为true，表示已经被占用了，如果为false，则表示该锁空闲
    private boolean initValue;

    private Collection<Thread> blockedThreadCollections = new ArrayList<>();

    public BooleanLock() {
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
    }

    @Override
    public void lock(long mills) throws InterruptedException, TimeOutException {

    }

    @Override
    public synchronized void unlock() {
        //释放锁的第一步，将标志位置为false
        this.initValue = false;
        log.info("Thread:{},release the lock",Thread.currentThread().getName());
        this.notifyAll();

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
