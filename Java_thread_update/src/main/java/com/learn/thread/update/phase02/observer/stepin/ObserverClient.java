package com.learn.thread.update.phase02.observer.stepin;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/6/30
 * comment:
 */
@Slf4j
public class ObserverClient {

    public static void main(String[] args) {
        final Subject subject = new Subject();
        new BinaryObserver(subject);
        new OctalObserver(subject);
        log.info("开始变化");
        subject.setState(10);
    }

}
