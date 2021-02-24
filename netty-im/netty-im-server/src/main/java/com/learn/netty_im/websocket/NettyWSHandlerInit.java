package com.learn.netty_im.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * autor:liman
 * createtime:2020/9/27
 * comment:netty中handler的initilizer
 */
public class NettyWSHandlerInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //websocket基于Http协议，需要所有HTTP编解码器
        pipeline.addLast(new HttpServerCodec());
        //加入对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        //对HttpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        // ====================== 以下是支持httpWebsocket ======================
        /**
         * websocket 服务器处理的协议，用于指定给客户端连接访问的路由 : /ws
         * 本handler会帮你处理一些繁重的复杂的事
         * 会帮你处理握手动作： handshaking（close, ping, pong） ping + pong = 心跳
         * 对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

//        pipeline.addLast(new NettyWebSocketServerHandler());
        pipeline.addLast(new ChatHandler());

        //加入自定义心跳检测handler
        pipeline.addLast(new IdleStateHandler(2,4,10));//针对客户端如果在指定时间内没有相关操作，则触发相关空闲事件
        pipeline.addLast(new HeartBeatHandler());
    }
}
