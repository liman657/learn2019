package com.learn.netty.objserial.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.MessagePack;

/**
 * autor:liman
 * createtime:2020/9/23
 * comment:Msgpack的编码器
 */
@Slf4j
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    /**
     *
     * @param ctx channelHandler的上下文
     * @param msg 传递过来的消息对象
     * @param out 输出对象
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        MessagePack messagePack = new MessagePack();
        byte[] writeByte = messagePack.write(msg);
        out.writeBytes(writeByte);
    }
}
