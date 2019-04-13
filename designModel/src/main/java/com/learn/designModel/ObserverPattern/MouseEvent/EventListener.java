package com.learn.designModel.ObserverPattern.MouseEvent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * autor:liman
 * comment: 观察者，真正的监听器
 */
public class EventListener {

    protected Map<String,Event> events = new HashMap<String,Event>();

    public void addListener(String eventType,Object target){
        try{
            this.addListener(eventType,target,target.getClass().getMethod("on"+toUpperFirstCase(eventType),Event.class));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addListener(String eventType,Object target,Method callback){
        events.put(eventType,new Event(target,callback));
    }

    private void trigger(Event event){
        event.setSource(this);
        event.setTime(System.currentTimeMillis());

        try{
            if(event.getCallback() !=null){
                event.getCallback().invoke(event.getTarget(),event);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void trigger(String trigger){
        if(!this.events.containsKey(trigger)){
            return;
        }
        Event event = this.events.get(trigger);
        event.setTrigger(trigger);
        trigger(event);
    }

    public String toUpperFirstCase(String str){
        char[] chars = str.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }

}
