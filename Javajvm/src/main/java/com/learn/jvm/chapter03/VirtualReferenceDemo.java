package com.learn.jvm.chapter03;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * autor:liman
 * createtime:2019/11/28
 * comment:虚引用
 * 最没有存在意义的引用，必须与队列一起使用
 */
public class VirtualReferenceDemo {

    public static VirtualReferenceDemo obj;

    static ReferenceQueue<VirtualReferenceDemo> phantomQueue = null;

    public static class CheckRefQueue extends Thread{
        @Override
        public void run() {
            while(true){
                if(phantomQueue!=null){
                    PhantomReference<VirtualReferenceDemo> phantomReference = null;
                    try{
                        phantomReference=(PhantomReference<VirtualReferenceDemo>) phantomQueue.remove();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(phantomReference!=null){
                        System.out.println("VirtualReferenceObj is delete by GC");
                    }
                }
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("VirtualReferenceDemo finalize called");
        obj = this;
    }

    @Override
    public String toString() {
        return "i am VirtualReferenceDemo";
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new CheckRefQueue();
        t.setDaemon(true);
        t.start();

        phantomQueue = new ReferenceQueue<VirtualReferenceDemo>();
        obj = new VirtualReferenceDemo();
        PhantomReference<VirtualReferenceDemo> phantomReference = new PhantomReference<VirtualReferenceDemo>(obj,phantomQueue);

        obj = null;
        System.gc();
        Thread.sleep(1000);

        if(obj == null){
            System.out.println("obj is null");
        }else{
            System.out.println("obj is not null");
        }
        System.out.println("第2次 GC");
        obj = null;
        System.gc();
        Thread.sleep(1000);
        if(obj == null){
            System.out.println("obj is null");
        }else{
            System.out.println("obj is not null");
        }
    }
}
