package com.learn.thread.update.phase02.readAndWrite;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/7/7
 * comment:
 */
@Slf4j
public class ReaderWorker extends Thread {
    private final SharedData data;

    public ReaderWorker(SharedData data){
        this.data = data;
    }

    @Override
    public void run() {
        try{
            while(true){
                char[] readBuf = data.read();
                log.info("{} reads {}",Thread.currentThread().getName(),String.valueOf(readBuf));
            }
        }catch (Exception e){
            log.error("{}",e);
        }
    }
}
