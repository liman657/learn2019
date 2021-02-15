package com.learn.netty_im.websocket;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * autor:liman
 * createtime:2021/2/15
 * comment:
 */
//@Data 如果要将json转换成对象，如果有嵌套的对象，嵌套的对象会无法读取属性
@ToString
public class MsgContent implements Serializable {

    private Integer action;		// 动作类型
    private ChatMsg chatMsg;	// 用户的聊天内容entity
    private String extand;		// 扩展字段

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public ChatMsg getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(ChatMsg chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getExtand() {
        return extand;
    }

    public void setExtand(String extand) {
        this.extand = extand;
    }
}
