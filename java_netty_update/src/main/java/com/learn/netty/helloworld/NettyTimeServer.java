package com.learn.netty.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * autor:liman
 * createtime:2020/8/17
 * comment:Netty的时间回显服务器
 */
@Slf4j
public class NettyTimeServer {

    private final int port;

    public NettyTimeServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
//        final NettyTimeServerHandler serverHandler = new NettyTimeServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();//这个相当于线程组
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();//这个相当于服务端的channel，等同于ServerScoketChannel
            serverBootstrap.group(group);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.localAddress(new InetSocketAddress(port));

            //服务端接收到连接请求，需要新开启一个channel通信（socket），每一个channel需要有自己的事件处理的handler
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
//                    socketChannel.pipeline().addLast(serverHandler);
                    socketChannel.pipeline().addLast(new NettyTimeServerHandler());
                }
            });
            //绑定到端口，阻塞等待直到连接完成
            ChannelFuture serverFuture =serverBootstrap.bind().sync();
            //阻塞，直到channel关闭
            serverFuture.channel().closeFuture().sync();

        }catch (Exception e){
            log.info("连接出现异常，异常信息为:{}",e);
        }finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) {
        int port = 9999;
        try {
            log.info("服务端启动，监听端口为:{}",port);
            new NettyTimeServer(port).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}