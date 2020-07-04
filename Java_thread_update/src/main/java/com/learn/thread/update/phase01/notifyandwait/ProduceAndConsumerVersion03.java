package com.learn.thread.update.phase01.notifyandwait;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2020/6/14
 * comment: 生产者和消费者最终版本
 */
@Slf4j
public class ProduceAndConsumerVersion03 {

    private int i = 0;
    private final Object LOCK = new Object();
    private volatile boolean isProduced = true;

    public void produce() {
        synchronized (LOCK) {
            if (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    log.error("InterruptedException:{}", e);
                }
            }

            i++;
            log.info("p->,{}", i);
            LOCK.notifyAll();
            isProduced = true;
        }
    }

    public void consume() {
        synchronized (LOCK) {
            if (!isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    log.error("InterruptedException:{}", e);
                }
            }
            log.info("C->,{}", i);
            LOCK.notifyAll();
            isProduced = false;
        }
    }

    public static void main(String[] args) {
        ProduceAndConsumerVersion03 pc = new ProduceAndConsumerVersion03();
        Stream.of("P1").forEach(n -> new Thread(n) {
                    @Override
                    public void run() {
                        while (true) {
                            pc.produce();
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                log.error("InterruptedException:{}", e);
                            }
                        }
                    }
                }.start()
        );

        Stream.of("C1", "C2", "C3", "C4","C5").forEach(n -> new Thread(n) {
                    @Override
                    public void run() {
                        while (true) {
                            pc.consume();
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                log.error("InterruptedException:{}", e);
                            }
                        }
                    }
                }.start()
        );
    }
}