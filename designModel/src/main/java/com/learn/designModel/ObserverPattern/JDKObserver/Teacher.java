package com.learn.designModel.ObserverPattern.JDKObserver;

import java.util.Observable;
import java.util.Observer;

/**
 * autor:liman
 * comment:
 */
public class Teacher implements Observer {

    private String name;
    public Teacher(String name){this.name = name;}

    @Override
    public void update(Observable o, Object arg) {
        Forum forum = (Forum)o;
        Question question = (Question)arg;
        System.out.println("================================");
        System.out.println("您收到了一个来自论坛的消息："+question.getUserName()+" 的提问:"+question.getContent());
    }
}
