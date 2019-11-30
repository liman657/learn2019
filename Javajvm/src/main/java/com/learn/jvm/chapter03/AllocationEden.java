package com.learn.jvm.chapter03;

/**
 * autor:liman
 * createtime:2019/11/26
 * comment: 验证对象优先分配在eden区
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC
 */
public class AllocationEden {

    private static final int _1MB = 1024*1024;

    public static void main(String[] args) {
        byte[] allocation01,allocation02,allocation03,allocation04;
        allocation01 = new byte[2*_1MB];
        allocation02 = new byte[2*_1MB];
        allocation03 = new byte[2*_1MB];
        allocation04 = new byte[4*_1MB];//这里会发生一次GC，分配失败
    }

}
