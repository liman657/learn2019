package com.learn.netty_im.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * autor:liman
 * createtime:2020/9/25
 * comment:WebSocket服务端启动类
 * springboot中整合Netty最单纯的就是将这个类交给spring管理，因此要保证只有一个
 * 容器交给了spring管理之后，不用我们手动停止Netty
 */
@Component
public class SpringBootNettyWSServer {
    //用内部类的方式构建单例
    private static class SingleSpringBootNettyWSServer{
        static final SpringBootNettyWSServer nettyWSServerInstance = new SpringBootNettyWSServer();
    }

    public static SpringBootNettyWSServer getInstance(){
        return SingleSpringBootNettyWSServer.nettyWSServerInstance;
    }

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    public SpringBootNettyWSServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class);
        server.childHandler(new NettyWSHandlerInit());
    }

    public void start() {
        future = server.bind(9098);
        System.err.println("netty websocket server 启动完毕...");
    }
}