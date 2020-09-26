package com.learn.netty.pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/9/19
 * comment:
 */
@Slf4j
public class SimpleOutHandlerB extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("出站处理器B，this is simple out handler B，write method：{}",msg);
        super.write(ctx, msg, promise);
    }
}
