package com.learn.netty.pipeline;

import com.google.errorprone.annotations.SuppressPackageLocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/9/19
 * comment:
 */
@Slf4j
public class SimpleInHandlerB extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("this is b simpleInHandler read method message:{}",msg);
        super.channelRead(ctx, msg);
    }
}
