package com.learn.thread.update.phase01.selfLock;

import java.util.Collection;

/**
 * autor:liman
 * createtime:2020/6/19
 * comment:
 */
public interface Lock {

    class TimeOutException extends Exception{
        public TimeOutException(String message) {
            super(message);
        }
    }

    void lock() throws InterruptedException;

    void lock(long mills) throws InterruptedException,TimeOutException;

    void unlock();

    Collection<Thread> getBlockedThread();

    int getBlockThreadSize();

}
