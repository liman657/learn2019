package com.learn.start;

/**
 * autor:liman
 * createtime:2019/6/25
 * comment:
 */
public class StartByExtends extends Thread {

    @Override
    public void run() {
        System.out.println("Started by extends thread");
    }

    public static void main(String[] args) {
        Thread thread = new StartByExtends();
        thread.start();
    }
}
