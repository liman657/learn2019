package com.learn.jvm.chapter07;

import java.nio.ByteBuffer;

/**
 * autor:liman
 * createtime:2019/12/3
 * comment: 直接内存溢出实例
 * -verbose:gc -Xmx128M -XX:+UseSerialGC -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 *
 * NIO 中的直接内存使用，也就是通过Java代码，获得一块堆外的内存空间，这块空间是直接向操作系统申请的。
 * 直接内存的申请速度一般要比堆内存慢，但是其访问速度会比堆内存块。因此，对于那些可复用的，并且会被经常访问的空间，
 * 使用直接内存是可以提高系统性能的。但是由于直接内存没有被Java虚拟机托管，如果使用不当也容易触发直接内存溢出。导致宕机
 *
 * 由于物理内存较大，这个无法导致分配失败
 *
 * 由于直接内存，不被JVM托管，因此直接内存不一定能触发GC
 * 因此保证直接内存不溢出的方法就是合理的进行FullGC，或者设置一个系统实际可达的-XX:MaxDirectMemorySize，也可以直接显示的GC
 */
public class DirectBufferOOM {

    public static void main(String[] args) {
        for(int i =0;i<1024;i++){
            ByteBuffer.allocateDirect(1024*1024);
            System.out.println(i);
            System.gc();//GC可以回收直接内存
        }
    }

}
