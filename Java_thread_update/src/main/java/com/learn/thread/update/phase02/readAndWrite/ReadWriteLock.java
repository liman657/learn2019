package com.learn.thread.update.phase02.readAndWrite;

/**
 * autor:liman
 * createtime:2020/7/7
 * comment:读写分离的锁
 */
public class ReadWriteLock {

    private int readingReaders = 0;//当前有几个线程在读数据
    private int waitingReaders = 0;//等待读的线程的个数
    private int writingWriters = 0;//正在写数据的线程的个数
    private int waitingWriters = 0;//等待写的线程的个数

    /**
     * 获取读锁
     * @throws InterruptedException
     */
    public synchronized void readLock() throws InterruptedException {
        //进来就增加一个等待读的线程
        this.waitingReaders++;
        try{
            while(writingWriters>0){
                this.wait();
            }
            this.readingReaders++;
        }finally {
            this.waitingReaders--;//
        }
    }

    /**
     * 释放读锁
     */
    public synchronized void readUnLock(){
        this.readingReaders--;
        this.notifyAll();
    }

    /**
     * 获取读锁
     * @throws InterruptedException
     */
    public synchronized void writeLock() throws InterruptedException {
        this.waitingWriters++;
        try{
            while(readingReaders>0 || writingWriters>0){
                this.wait();
            }
            this.writingWriters++;
        }finally {
            this.waitingWriters--;
        }
    }

    /**
     * 释放读锁
     */
    public synchronized void writeUnLock(){
        this.writingWriters--;
        this.notifyAll();
    }

}
