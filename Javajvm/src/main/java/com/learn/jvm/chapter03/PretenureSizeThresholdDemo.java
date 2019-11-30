package com.learn.jvm.chapter03;

/**
 * autor:liman
 * createtime:2019/11/26
 * comment: 验证大对象直接进入老年代
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:PretenureSizeThreshold=3145728
 * PretenureSizeThreshold这个参数只对Serial和ParNew两款垃圾收集器有效
 */
public class PretenureSizeThresholdDemo {

    private static final int _1MB = 1024*1024;

    public static void main(String[] args) {
        byte[] allocation;
        allocation=new byte[4*_1MB];
    }

}
