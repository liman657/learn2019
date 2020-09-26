package com.learn.netty.decodeAndEncode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/9/25
 * comment:简单的业务处理，这里接受解码之后的数据
 */
@Slf4j
public class Byte2CharStringHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("处理解码之后的数据为：{}",msg);
    }
}
