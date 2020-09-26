package com.learn.netty.halfAndstickPack.fixedlength;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/8/18
 * comment:
 */
@Slf4j
@ChannelHandler.Sharable
public class FixLengthTimeClientHandler extends ChannelInboundHandlerAdapter {

    private static int count;

    /**
     * 客户端读取到数据之后干什么
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req,"UTF-8");
        log.info("receive message from server,now is {},the counter is {}",body,++count);
    }

    /**
     * 当客户端被通知channel活跃以后可以做的事情
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接建立，开始发送数据");
        ByteBuf message = null;
        byte[] request = ("QUERY TIME").getBytes();
        for(int i=0;i<100;i++){
            message = Unpooled.buffer(request.length);
            message.writeBytes(request);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("消息读取完毕");
        super.channelReadComplete(ctx);
    }

    /**
     * 异常处理的逻辑
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("异常，异常信息为:{}",cause.fillInStackTrace());
        cause.printStackTrace();
        ctx.close();
    }
}
