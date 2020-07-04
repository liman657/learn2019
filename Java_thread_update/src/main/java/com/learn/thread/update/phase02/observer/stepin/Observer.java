package com.learn.thread.update.phase02.observer.stepin;

/**
 * autor:liman
 * createtime:2020/6/30
 * comment:
 */
public abstract class Observer {

    protected Subject subject;

    public Observer(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public abstract void update();

}
