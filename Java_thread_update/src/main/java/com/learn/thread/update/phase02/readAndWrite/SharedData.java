package com.learn.thread.update.phase02.readAndWrite;

/**
 * autor:liman
 * createtime:2020/7/7
 * comment:
 */
public class SharedData {

    private final char[] buffer;

    private final ReadWriteLock lock = new ReadWriteLock();

    public SharedData(int size) {
        this.buffer = new char[size];
        for(int i = 0;i<size;i++){
            this.buffer[i] = '*';
        }
    }

    public char[] read() throws InterruptedException {
        try{
            lock.readLock();
            return this.doRead();
        }finally {
            lock.readUnLock();
        }
    }

    private char[] doRead() {
        char[] newBuf = new char[buffer.length];
        for(int i =0;i<buffer.length;i++){
            newBuf[i]=buffer[i];
        }
        slowly(50);
        return newBuf;
    }

    public void write(char c) throws InterruptedException {
        try{
            lock.writeLock();
            this.doWrite(c);
        }finally {
            lock.writeUnLock();
        }

    }

    private void doWrite(char c) {
        for(int i =0;i<buffer.length;i++){
            buffer[i] = c;
            slowly(10);
        }

    }

    private void slowly(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
