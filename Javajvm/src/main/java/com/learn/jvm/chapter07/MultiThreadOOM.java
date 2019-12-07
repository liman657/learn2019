package com.learn.jvm.chapter07;

/**
 * autor:liman
 * createtime:2019/12/3
 * comment:线程过多导致OOM
 * -verbose:gc -Xmx128M -Xss512K -XX:+UseSerialGC -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 * 实验结果：自己的电脑直接崩溃重启了
 *
 * 解决办法：
 * 1、尝试减少堆的空间，使用-Xmx，这样操作系统可以预留更多的内存用户创建线程，因此程序可正常运行
 * 2、减少每一个线程所占的空间，使用-Xss参数，可以指定线程的栈空间
 */
public class MultiThreadOOM {

    public static class sleepThread implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(100000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        for(int i =0;i<1500;i++){
            Thread t =new Thread(new sleepThread(),"Thread"+i);
            t.start();
            System.out.println("Thread "+i+" created");
        }
    }

}
