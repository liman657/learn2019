package com.learn.thread.update.phase02.observer.stepin;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/6/30
 * comment:
 */
@Slf4j
public class OctalObserver extends Observer {

    public OctalObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        log.info("octal receive message,target state:{}",subject.getState());
    }
}
