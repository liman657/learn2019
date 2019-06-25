package com.learn.start;

/**
 * autor:liman
 * createtime:2019/6/25
 * comment:
 */
public class StartByImpl implements Runnable {
    @Override
    public void run() {
        System.out.println("start by implement Runnable");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new StartByImpl());
        thread.start();
    }
}
