package com.learn.designModel.ObserverPattern.JDKObserver;

import java.util.Observable;

/**
 * autor:liman
 * mobilNo:15528212893
 * mail:657271181@qq.com
 * comment:
 */
public class Forum extends Observable {

    private String name = "提问论坛";

    private static Forum forum = null;

    private Forum(){}

    public static Forum getForum(){
        if(forum == null){
            forum=new Forum();
        }
        return forum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 发布问题
     */
    public void publicQuestion(Question question){
        System.out.println(question.getUserName()+":发布了一个问题，"+question.getContent()+"!");

        //设置变化
        setChanged();
        notifyObservers(question);
    }
}
