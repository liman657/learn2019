package com.learn.jvm.chapter03;

import java.lang.ref.WeakReference;

/**
 * autor:liman
 * createtime:2019/11/28
 * comment: 弱引用实例
 * -Xms16M -Xmx16M -Xmn8M -XX:SurvivorRatio=8
 * 弱引用，发现之后，不管如何都会被GC回收
 */
public class WeakReferenceDemo {

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
        WeakReference<User> userWeakReference = new WeakReference<User>(u);
        u = null;
        System.out.println(userWeakReference.get());
        System.gc();
        System.out.println("After GC:");
        System.out.println(userWeakReference.get());
    }

}
