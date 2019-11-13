package com.learn.IMChat.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * autor:liman
 * createtime:2019/11/4
 * comment:消息编码器
 */
public class IMMessageEncoder extends MessageToByteEncoder<IMMessage> {

    protected void encode(ChannelHandlerContext channelHandlerContext, IMMessage imMessage, ByteBuf out) throws Exception {
        out.writeBytes(new MessagePack().write(imMessage));
    }

    public String encode(IMMessage msg){
        if(null == msg){ return ""; }
        String prex = "[" + msg.getCmd() + "]" + "[" + msg.getTime() + "]";
        if(IMEnum.LOGIN.getName().equals(msg.getCmd()) ||
                IMEnum.FLOWER.getName().equals(msg.getCmd())){
            prex += ("[" + msg.getSender() + "][" + msg.getTerminal() + "]");
        }else if(IMEnum.CHAT.getName().equals(msg.getCmd())){
            prex += ("[" + msg.getSender() + "]");
        }else if(IMEnum.SYSTEM.getName().equals(msg.getCmd())){
            prex += ("[" + msg.getOnline() + "]");
        }
        if(!(null == msg.getContent() || "".equals(msg.getContent()))){
            prex += (" - " + msg.getContent());
        }
        return prex;
    }
}
