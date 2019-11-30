package com.learn.jvm.chapter03;

import java.lang.ref.SoftReference;

/**
 * autor:liman
 * createtime:2019/11/28
 * comment:软引用实例
 * -Xms16M -Xmx16M -Xmn8M -XX:SurvivorRatio=8
 * 软引用：被GC标记之后，如果内存分配不足的时候才会被回收
 */
public class SoftRefDemo {

    public static class User{
        public User(int id,String name){
            this.id = id;
            this.name=name;
        }

        private int id;
        private String name;

        private byte[] data = new byte[1024*1024];

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        User u = new User(1,"liman");
        SoftReference<User> userSoftReference = new SoftReference<User>(u);//构建一个软引用
        u = null;   //去除强引用

        System.out.println(userSoftReference.get()); //从软引用中获取对象
        System.gc();    //进行第一次GC
        System.out.println("After GC:");
        System.out.println(userSoftReference.get());    //垃圾回收之后，从软引用中获取对象

        byte[] b = new byte[1024*1024*7];    //分配大对象，系统会自动进行GC
        System.gc();
        System.out.println(userSoftReference.get());    //还是从软引用中获得对象
    }

}
