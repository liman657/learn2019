package com.learn.designModel.ObserverPattern.OnlineProblem;

import java.lang.reflect.Method;

/**
 * autor:liman
 * comment: 被观察者与观察者之间传递的消息实体
 */
public class Message {

    private Object source;
    private Object target;
    private String event;
    private Method callback;
    private Long occurTime;

    public Message(Object target, Method callback) {
        this.target = target;
        this.callback = callback;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Method getCallback() {
        return callback;
    }

    public void setCallback(Method callback) {
        this.callback = callback;
    }

    public Long getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Long occurTime) {
        this.occurTime = occurTime;
    }

    @Override
    public String toString() {
        return "Message{" +"\n"+
                "source=" + source +"\n"+
                ", target=" + target +"\n"+
                ", event='" + event + '\'' +"\n"+
                ", callback=" + callback +"\n"+
                ", occurTime=" + occurTime +"\n"+
                '}';
    }
}
