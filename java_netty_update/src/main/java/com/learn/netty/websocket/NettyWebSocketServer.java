package com.learn.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * autor:liman
 * createtime:2020/9/25
 * comment:WebSocket服务端启动类
 */
@Slf4j
public class NettyWebSocketServer {

    public static void main(String[] args) {
        new NettyWebSocketServer().start(9908,"127.0.0.1");
    }

    public void start(int port, String host) {
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        EventLoopGroup subGroup = new NioEventLoopGroup();
        try {
            log.info("服务端启动");
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(mainGroup, subGroup);
            serverBootstrap.localAddress(new InetSocketAddress(host,port));
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
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

                    pipeline.addLast(new NettyWebSocketServerHandler());
                }
            });

            ChannelFuture serverFuture = serverBootstrap.bind(port).sync();
            serverFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("服务端出现异常，异常信息为:{}", e);
        } finally {
            subGroup.shutdownGracefully();
            mainGroup.shutdownGracefully();
        }
    }

}
