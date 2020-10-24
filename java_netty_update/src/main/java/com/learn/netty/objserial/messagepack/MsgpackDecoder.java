package com.learn.netty.objserial.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.MessagePack;
import org.msgpack.type.Value;

import java.util.List;

/**
 * autor:liman
 * createtime:2020/9/23
 * comment:MsgpackDecoder解码器
 * 这里的泛型参数代表接受的消息类型
 */
@Slf4j
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final int readableLength = msg.readableBytes();
        final byte[] dataArray;
        dataArray = new byte[readableLength];
        /**
         * 这里使用的是ByteBuf的getBytes方法来将ByteBuf对象转换为字节数组，前面是使用readBytes，直接传入一个接收的字节数组参数即可
         * 这里的参数比较多，第一个参数是index，关于readerIndex，说明如下：
         * ByteBuf是通过readerIndex跟writerIndex两个位置指针来协助缓冲区的读写操作的，具体原理等到Netty源码分析时再详细学习一下
         * 第二个参数是接收的字节数组
         * 第三个参数是dstIndex the first index of the destination
         * 第四个参数是length   the number of bytes to transfer
         */
        msg.getBytes(msg.readerIndex(),dataArray,0,readableLength);
        MessagePack messagePack = new MessagePack();
        out.add(messagePack.read(dataArray));
    }
}
