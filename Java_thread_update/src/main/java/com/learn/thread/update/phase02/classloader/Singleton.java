package com.learn.thread.update.phase02.classloader;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/6/27
 * comment: 类加载过程的一个实例
 */
@Slf4j
public class Singleton {

    private static Singleton instance = new Singleton();

    private static int x = 0;
    private static int y;

//    private static Singleton instance = new Singleton();

    private Singleton(){
        x++;
        y++;
    }

    public static Singleton getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        log.info("x:{}",singleton.x);
        log.info("y:{}",singleton.y);
    }
}
