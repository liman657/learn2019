package com.learn.thread.update.phase01.stepin;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2020/5/31
 * comment:create thread step in
 */
@Slf4j
public class StepIn {

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                enjoyMusic();
            }
        }.start();
        browseNews();
    }

    private static void browseNews(){
        while(true){
            log.info("Uh-hh,the good news.");
            sleep(1);
        }
    }

    private static void enjoyMusic(){
        while(true){
            log.info("Uh-hh,the nice music.");
            sleep(1);
        }
    }

    private static void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}