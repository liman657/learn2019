package com.learn.thread.update.phase02.readAndWrite;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * autor:liman
 * createtime:2020/7/7
 * comment:
 */
@Slf4j
public class WriterWorker extends Thread {
    private static final Random random = new Random(System.currentTimeMillis());
    private final SharedData data;
    private final String filler;
    private int index = 0;

    public WriterWorker(SharedData data,String filler){
        this.data = data;
        this.filler = filler;
    }

    @Override
    public void run() {
        try{
            while(true){
                char c= nextChar();
                data.write(c);
                Thread.sleep(random.nextInt(1000));
            }
        }catch (Exception e){
            log.error("{}",e);
        }
    }

    private char nextChar(){
        char c= filler.charAt(index);
        index++;
        if(index >= filler.length()){
            index = 0;
        }
        return c;
    }
}
