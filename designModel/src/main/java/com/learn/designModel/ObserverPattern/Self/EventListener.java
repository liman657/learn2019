package com.learn.designModel.ObserverPattern.Self;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * autor:liman
 * comment:
 */
public class EventListener {

    private Map<String,Event> eventMap = new HashMap<>();

    public void addListener(String eventType,Object target,Method callback){
        this.eventMap.put(eventType,new Event(target,callback));
    }

    public void trigger(Event e){
        //因为子类会继承这个类，所以这里赋值为this
        e.setSource(this);
        e.setTime(System.currentTimeMillis());
        try {
            e.getCallback().invoke(e.getTarget(),e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void trigger(String eventType){
        if(!eventMap.containsKey(eventType)){
            return;
        }
        Event e = eventMap.get(eventType);
        trigger(e);
    }

}
