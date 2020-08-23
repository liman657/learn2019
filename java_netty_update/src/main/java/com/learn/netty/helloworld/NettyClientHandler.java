package com.learn.netty.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/8/18
 * comment:
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /**
     * 客户端读取到数据之后干什么
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        //简单打印从服务端来的数据
        log.info("client accept info : {}",byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 当客户端被通知channel活跃以后可以做的事情
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("QUERY TIME",CharsetUtil.UTF_8));
//        super.channelActive(ctx);
    }

    /**
     * 异常处理的逻辑
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
