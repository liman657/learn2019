package com.learn.jvm.chapter03;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Arrays;

/**
 * autor:liman
 * createtime:2019/11/28
 * comment:软引用附带的队列
 * -Xms16M -Xmx16M -Xmn8M -XX:SurvivorRatio=8
 */
public class SoftRefQueueDemo {

    public static class User{
        private int id;
        private String name;
        private byte[] data = new byte[1024*1024];

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    static ReferenceQueue<User> softQueue=null;

    /**
     * 一个自定义的软引用类，通过uid知道那个user对象被回收了
     */
    public static class UserSoftReference extends SoftReference<User>{

        int uid;

        public UserSoftReference(User referent, ReferenceQueue<? super User> q) {
            super(referent, q);
            uid = referent.id;
        }


    }

    public static class CheckRefQueue extends Thread{
        @Override
        public void run() {
            while(true){
                if(softQueue!=null){
                    UserSoftReference obj = null;
                    try{
                        obj = (UserSoftReference)softQueue.remove();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if(obj!=null){
                        System.out.println("user id "+obj.uid+" is delete");
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new CheckRefQueue();
        t.setDaemon(true);
        t.start();
        User u = new User(1,"liman");
        softQueue = new ReferenceQueue<User>();
        UserSoftReference userSoftReference = new UserSoftReference(u,softQueue);
        u = null;
        System.out.println(userSoftReference.get());
        System.gc();
        //内存足够，不会被回收
        System.out.println("After GC:");
        System.out.println(userSoftReference.get());

        System.out.println("try to create byte array and GC");
        byte[] b = new byte[1024*1024*7];
        System.gc();
        System.out.println(userSoftReference.get());
        Thread.sleep(1000);
    }

}
