package com.learn.condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author:liman
 * createtime:2019/7/11
 */
public class ConditionTest {

    public static class Basket{
        Lock lock = new ReentrantLock();
        Condition producer = lock.newCondition();
        Condition consumer = lock.newCondition();
        int num = 0;
        /**
         * 生产者
         * @throws InterruptedException
         */
        public void produce() throws InterruptedException {
            lock.lock();
            System.out.println("producer get a lock......");
            try{
                while(num==1){
                    System.out.println("producer sleep ...");
                    producer.await();
                    System.out.println("producer awaked...");
                }

                Thread.sleep(500);
                System.out.println("producer produced a Apple");
                num = 1;
                consumer.signal();
            }finally {
                lock.unlock();
            }
        }

        /**
         * 消费苹果
         * @throws InterruptedException
         */
        public void consumer() throws InterruptedException {
            lock.lock();
            System.out.println("consumer get a lock ...");
            try{
                while(num==0){
                    //如果没苹果，则无法消费，需要阻塞
                    System.out.println("consumer sleep ...");
                    consumer.await();
                    System.out.println("consumer awaked");
                }
                Thread.sleep(500);
                System.out.println("consumer consumed an Apple");
                num = 0;
                producer.signal();
            }finally {
                lock.unlock();
            }
        }
    }

    /**
     * 测试basket程序
     */
    public static void testBasket(){
        final Basket basket = new Basket();
        Runnable producer = new Runnable() {
            @Override
            public void run() {
                try {
                    basket.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable consumer = new Runnable() {
            @Override
            public void run() {
                try {
                    basket.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i = 0;i<1;i++){
            service.submit(producer);
        }
        for(int i=0;i<1;i++){
            service.submit(consumer);
        }
        service.shutdown();
    }

    public static void main(String[] args) {
        ConditionTest.testBasket();
    }
}