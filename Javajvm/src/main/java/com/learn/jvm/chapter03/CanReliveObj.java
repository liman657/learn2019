package com.learn.jvm.chapter03;

/**
 * autor:liman
 * createtime:2019/11/28
 * comment:对象其实是可以复活的
 */
public class CanReliveObj {

    public static CanReliveObj obj;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("CanReliveObj finalize called");
        obj = this; //将obj修改成了强引用，obj复活
    }

    @Override
    public String toString() {
        return "I am CanReliveObj";
    }

    public static void main(String[] args) throws InterruptedException {
        obj = new CanReliveObj();
        obj = null;
        System.gc();    //第一次调用GC，会触发CanReliveObj的finalize方法
        Thread.sleep(1000);
        if(obj==null){
            System.out.println("obj is null");
        }else{
            System.out.println("obj is avaliable");
        }
        System.out.println("第二次GC");
        obj = null;
        System.gc();
        Thread.sleep(1000);
        if(obj==null){
            System.out.println("obj is null");
        }else{
            System.out.println("obj is avaliable");
        }
    }
}
