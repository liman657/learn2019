package com.learn.thread.update.phase02.observer.stepin;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/6/30
 * comment:
 */
@Slf4j
public class BinaryObserver extends Observer {


    public BinaryObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        log.info("binary receive message,target state:{}",subject.getState());
    }
}
