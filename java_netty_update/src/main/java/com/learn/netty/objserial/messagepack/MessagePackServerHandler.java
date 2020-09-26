package com.learn.netty.objserial.messagepack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/9/23
 * comment: 服务端的业务处理类
 */
@Slf4j
public class MessagePackServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("服务器收到的消息:{}",msg);
        ctx.writeAndFlush(msg);//直接返回给客户端
//        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
//        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("连接出现异常，异常信息为:{}",cause);
        ctx.close();
//        super.exceptionCaught(ctx, cause);
    }
}
