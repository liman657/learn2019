package com.learn.jvm.chapter02;

/**
 * autor:liman
 * createtime:2019/11/24
 * comment:局部变量表中的变量也是GC中的root节点
 * -XX:+PrintGC——打印GC日志
 */
public class LocalValGC {

    public void localValGc01(){
        byte[] a = new byte[6*1024*1024];
        System.gc();
    }

    public void localValGc02(){
        byte[] a = new byte[6*1024*1024];
        a=null;
        System.gc();
    }

    public void localValGc03() {
        {
            byte[] a = new byte[6*1024*1024];
        }
        System.gc();
    }

    public void localValGc04() {
        {
            byte[] a = new byte[6*1024*1024];
        }
        int c= 10;
        System.gc();
    }

    public void localValGc05() {
        localValGc01();
        System.gc();
    }

    public static void main(String[] args) {
        LocalValGC localValGC = new LocalValGC();
        localValGC.localValGc01();
    }

}
