package com.learn.netty.decodeAndEncode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * autor:liman
 * createtime:2020/9/25
 * comment:自定义的解码器，String解码器，将byte中的数据，按位转换成String
 */
@Slf4j
public class IntValuePlus100Decoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int i = in.readInt();
        log.info("readable length : {}", in.readableBytes());
        i = i + 100;
        out.add(i);
        log.info("解码之后的数据为:{}", i);
    }
}
