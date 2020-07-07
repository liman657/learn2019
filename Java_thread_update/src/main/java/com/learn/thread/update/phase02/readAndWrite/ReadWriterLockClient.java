package com.learn.thread.update.phase02.readAndWrite;

/**
 * autor:liman
 * createtime:2020/7/7
 * comment:
 */
public class ReadWriterLockClient {
    public static void main(String[] args) {
        final SharedData sharedData = new SharedData(10);
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new ReaderWorker(sharedData).start();
        new WriterWorker(sharedData, "qwertyuiopasdfg").start();
        new WriterWorker(sharedData, "QWERTYUIOPASDFG").start();
    }
}
