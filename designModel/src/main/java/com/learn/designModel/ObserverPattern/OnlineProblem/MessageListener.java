package com.learn.designModel.ObserverPattern.OnlineProblem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * autor:liman
 * comment:用于关联事件类型与消息体
 */
public class MessageListener {

    private Map<String,Message> eventToMessage = new HashMap<>();

    public void addListener(String messageType,Object target,Method callback){
        eventToMessage.put(messageType,new Message(target,callback));
    }

    /**
     * 内部调用的trigger
     * @param message
     */
    public void trigger(Message message){
        //因为子类会继承这个类，子类就是被观察者，所以这里赋值为this
        message.setSource(this);
        message.setOccurTime(System.currentTimeMillis());
        try {
            message.getCallback().invoke(message.getTarget(),message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param messageType 消息类型
     */
    public void trigger(String messageType){
        if(!eventToMessage.containsKey(messageType)){
            return;
        }
        Message message = eventToMessage.get(messageType);
        message.setEvent(messageType);
        trigger(message);
    }

}
