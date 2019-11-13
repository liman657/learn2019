package com.learn.IMChat.protocol;

/**
 * autor:liman
 * createtime:2019/11/4
 * comment:
 */
public enum IMEnum {
    /** 系统消息 */
    SYSTEM("SYSTEM"),
    /** 登录指令 */
    LOGIN("LOGIN"),
    /** 登出指令 */
    LOGOUT("LOGOUT"),
    /** 聊天消息 */
    CHAT("CHAT"),
    /** 送鲜花 */
    FLOWER("FLOWER");

    private String name;

    public static boolean isIMP(String content){
        //判断我们是否支持这个协议
        //简单的判断消息头即可
        return content.matches("^\\[(SYSTEM|LOGIN|LOGIN|CHAT)\\]");
    }

    IMEnum(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String toString(){
        return this.name;
    }
}
