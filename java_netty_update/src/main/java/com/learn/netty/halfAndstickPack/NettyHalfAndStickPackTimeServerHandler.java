package com.learn.netty.halfAndstickPack;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * autor:liman
 * createtime:2020/8/17
 * comment:
 */
@Slf4j
//@ChannelHandler.Sharable //让这个处理类在多线程情况下处理，如果没有这个注解，则只能处理一个客户端的连接
public class NettyHalfAndStickPackTimeServerHandler extends ChannelInboundHandlerAdapter {

    private static int totalConnectCount;

    /**
     * 服务端读取到网络数据之后的处理逻辑
     *
     * @param ctx
     * @param msg
     * @throws Exception 如果客户端数据很多，在一个方法中读不完，就会出现拆包的操作
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req, "UTF-8");
//        log.info("the time server receive the order from client : {}", body);
//        String currentTime = "";
//        if ("QUERY TIME".equalsIgnoreCase(body)) {
//            currentTime = LocalDateTime.now().toString();
//        } else {
//            currentTime = "BAD REQUIRE";
//        }

        ByteBuf resp = sendMessage2Client(msg);
        ctx.write(resp);
//        super.channelRead(ctx,msg);
    }


    /**
     * 服务端读取完成网络数据之后的处理逻辑
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //在服务器写完数据之后，addListener中会关闭连接
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 发生异常之后的处理逻辑
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public ByteBuf sendMessage2Client(Object msg) {
        ByteBuf resp = Unpooled.buffer(1024);
        try {
            ByteBuf buf = (ByteBuf) msg;
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req, "UTF-8");
//            String body = (String) msg;
//            String finalCommand = body.substring(0,req.length-System.getProperty("line.separator").length());
            log.info("the time server receive the order from client : {},the counter is {}", body, ++totalConnectCount);
            String currentTime = "";
            if ("QUERY TIME".equalsIgnoreCase(body)) {
                currentTime = LocalDateTime.now().toString();
            } else {
                currentTime = "BAD REQUIRE";
            }
            //加入拆包的LineBasedFrameDecoder之后，服务端返回给客户端的报文中也要有分隔符，否则客户端readComplete会调用多次，而不会去调用channelRead
            currentTime += System.getProperty("line.separator");
            resp = Unpooled.copiedBuffer(currentTime.getBytes());
        } catch (Exception e) {
            log.error("发送消息异常，异常信息为:{}", e);
        }
        return resp;
    }
}
