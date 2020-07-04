package com.learn.thread.update.phase02.observer.observerthread;

/**
 * autor:liman
 * createtime:2020/7/1
 * comment:可被监控的对象
 */
public interface Observable {

    enum Cycle{
        STARTED,RUNNING,DONE,ERROR
    }

}
