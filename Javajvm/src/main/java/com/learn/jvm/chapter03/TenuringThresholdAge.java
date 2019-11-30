package com.learn.jvm.chapter03;

/**
 * autor:liman
 * createtime:2019/11/26
 * comment: 验证长期存活的对象进入老年代
 * -verbose:gc -Xms80M -Xmx80M  -Xmn40M -XX:SurvivorRatio=8 -XX:+UseSerialGC -XX:+PrintGCDetails -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 */
public class TenuringThresholdAge {

    private static final int _1MB = 1024 * 1024;

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        byte[] allocation01, allocation02, allocation03,allocation04;
        allocation01 = new byte[_1MB];
        allocation02 = new byte[_1MB ];
        allocation03 = new byte[16 * _1MB];  //这里发生第一次GC
        allocation04 = new byte[16 * _1MB];  //这里发生第一次GC

        allocation04 = null;
        allocation04 = new byte[16 * _1MB];  //这里发生第二次GC
    }

}
