package com.learn.springbootwebsocket.controller;

import com.learn.springbootwebsocket.entity.InMessage;
import com.learn.springbootwebsocket.entity.OutMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * autor:liman
 * createtime:2020/10/26
 * comment:
 */
@Controller
public class GameController {

    @MessageMapping("/gonggao/chat")
    @SendTo("/topic/game_chat")
    public OutMessage gameInfo(InMessage message){

        return new OutMessage(message.getContent());
    }


}
