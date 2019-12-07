package com.learn.jvm.chapter07;

import java.util.ArrayList;

/**
 * autor:liman
 * createtime:2019/12/3
 * comment:简单的堆溢出实例
 * -verbose:gc -Xms32M -Xmx32M -Xmn16M -XX:+UseSerialGC -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 */
public class SimpleHeapOOM {

    public static void main(String[] args) {
        ArrayList<byte[]> list = new ArrayList<byte[]>();
        for(int i=0;i<1024;i++){
            list.add(new byte[1024*1024]);
        }
    }

}
