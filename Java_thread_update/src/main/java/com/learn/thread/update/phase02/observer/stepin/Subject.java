package com.learn.thread.update.phase02.observer.stepin;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * autor:liman
 * createtime:2020/6/30
 * comment:主体，
 */
@Slf4j
public class Subject {

    private List<Observer> observers = new ArrayList<>();

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        if(state!=this.state) {
            this.state = state;
            notifyAllObservers();
        }
    }

    public void attach(Observer observer){
        this.observers.add(observer);
    }

    private void notifyAllObservers(){
        observers.stream().forEach(Observer::update);
    }
}
