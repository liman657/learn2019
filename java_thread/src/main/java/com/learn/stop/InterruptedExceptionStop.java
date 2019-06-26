package com.learn.stop;

/**
 * autor:liman
 * createtime:2019/6/26
 * comment:
 */
public class InterruptedExceptionStop {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("线程运行中......");

                    //如果想停止线程，这里抛出中断异常
                    throw new InterruptedException();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程终止......");
        });

        thread.start();
        Thread.sleep(3);
        thread.interrupt();
    }

}
