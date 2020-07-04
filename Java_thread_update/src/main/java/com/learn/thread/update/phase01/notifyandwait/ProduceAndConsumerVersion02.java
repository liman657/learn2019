package com.learn.thread.update.phase01.notifyandwait;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2020/6/14
 * comment:生产者和消费者，
 */
@Slf4j
public class ProduceAndConsumerVersion02 {

    private int i = 0;

    private final Object LOCK = new Object();

    //引入一个标记，表示是否生产了数据
    private volatile boolean isProduced = true;

    public void produce() {
        synchronized (LOCK) {
            //如果存在数据，则阻塞
            if (isProduced) {
                try {
                    log.info("生产者线程:{} 等待", Thread.currentThread().getName());
                    LOCK.wait();
                    log.info("生产者线程:{} 结束等待", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    log.error("InterruptedException:{}", e);
                }
//            } else {//如果不存在数据，则需要生产数据
//
//            }
            }
            i++;
            log.info("{} produce -> {}", Thread.currentThread().getName(), i);
            LOCK.notifyAll();
            isProduced = true;
        }
    }

    public void consume() {
        synchronized (LOCK) {
            //如果有数据，则需要消费数据
//            if (isProduced) {
//                log.info("{} consume -> {}", Thread.currentThread().getName(),i);
//                LOCK.notifyAll();
//                isProduced = false;
//            } else {//如果没有数据，则需要阻塞，等待生产者产生数据
//                try {
//                    log.info("消费者线程:{} 等待",Thread.currentThread().getName());
//                    LOCK.wait();
//                    log.info("消费者线程:{} 结束等待",Thread.currentThread().getName());
//                } catch (InterruptedException e) {
//                    log.error("InterruptedException:{}", e);
//                }
//            }
            if (!isProduced) {//如果没有数据，则需要阻塞，等待生产者产生数据
                try {
                    log.info("消费者线程:{} 等待", Thread.currentThread().getName());
                    LOCK.wait();
                    log.info("消费者线程:{} 结束等待", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    log.error("InterruptedException:{}", e);
                }
            }
            log.info("{} consume -> {}", Thread.currentThread().getName(), i);
            LOCK.notifyAll();
            isProduced = false;

        }
    }

    public static void main(String[] args) {
        ProduceAndConsumerVersion02 pc = new ProduceAndConsumerVersion02();
        Stream.of("P1", "P2").forEach(n -> {
            new Thread(n) {
                @Override
                public void run() {
                    while (true) {
                        pc.produce();
                    }
                }
            }.start();
        });

        Stream.of("C1", "C2", "C3", "C4", "C5").forEach(n -> {
            new Thread(n) {
                @Override
                public void run() {
                    while (true) {
                        pc.consume();
                    }
                }
            }.start();
        });
    }
}