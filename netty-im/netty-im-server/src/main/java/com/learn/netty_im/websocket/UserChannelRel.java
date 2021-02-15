package com.learn.netty_im.websocket;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * autor:liman
 * createtime:2021/2/15
 * comment: 用户id与通信channel的关联关系映射类
 */
@Slf4j
public class UserChannelRel {

    private  static HashMap<String,Channel> managerUserChannelMap = new HashMap<>();

    public static void putUserChannel(String userId,Channel channel){
        managerUserChannelMap.put(userId,channel);
    }

    public static Channel getUserChannel(String userId){
        return managerUserChannelMap.get(userId);
    }

    public static void output() {
        for (HashMap.Entry<String, Channel> entry : managerUserChannelMap.entrySet()) {
            System.out.println("UserId: " + entry.getKey()
                    + ", ChannelId: " + entry.getValue().id().asLongText());
        }
    }

}
