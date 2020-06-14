package com.learn.thread.update.synchronizeddemo;

/**
 * autor:liman
 * createtime:2020/6/14
 * comment:
 */
public class ClassMonitorMain {

    public static void main(String[] args) {
        new Thread(ClassMonitorUpdate::method01,"T1").start();
        new Thread(ClassMonitorUpdate::method02,"T2").start();
        new Thread(ClassMonitorUpdate::method03,"T3").start();
    }
}