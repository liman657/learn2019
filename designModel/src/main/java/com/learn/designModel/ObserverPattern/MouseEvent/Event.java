package com.learn.designModel.ObserverPattern.MouseEvent;

import java.lang.reflect.Method;

/**
 * autor:liman
 * comment:监听器的一种包装,标准事件源格式的定义
 */
public class Event {

    //事件源
    private Object source;
    //需要通知的目标
    private Object target;
    //事件触发需要处理的动作
    private Method callback;
    //事件的名称，需要触发的条件
    private String trigger;
    //时间触发时间
    private Long time;

    public Event(Object target, Method callback) {
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

    public Method getCallback() {
        return callback;
    }

    public void setCallback(Method callback) {
        this.callback = callback;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Event{" +
                "source=" + source +
                ", target=" + target +
                ", callback=" + callback +
                ", trigger='" + trigger + '\'' +
                ", time=" + time +
                '}';
    }
}
