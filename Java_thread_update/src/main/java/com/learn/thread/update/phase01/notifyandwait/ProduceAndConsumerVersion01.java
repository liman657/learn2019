package com.learn.thread.update.phase01.notifyandwait;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/6/14
 * comment:生产者消费者初始版本，没有通知机制
 */
@Slf4j
public class ProduceAndConsumerVersion01 {

    private int i = 1;

    private final Object LOCK=new Object();

    private void produce(){
        synchronized (LOCK){
            log.info("P->,{}",i++);
        }
    }

    private void consume(){
        synchronized (LOCK){
            log.info("C->,{}",i);
        }
    }

    public static void main(String[] args) {
        ProduceAndConsumerVersion01 pc = new ProduceAndConsumerVersion01();

        new Thread("P"){
            @Override
            public void run() {
                while(true){
                    pc.produce();
                }
            }
        }.start();

        new Thread("C"){
            @Override
            public void run() {
                while(true){
                    pc.consume();
                }
            }
        }.start();
    }
}