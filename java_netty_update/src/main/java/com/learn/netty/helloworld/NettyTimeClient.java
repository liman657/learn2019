package com.learn.netty.helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * autor:liman
 * createtime:2020/8/18
 * comment:
 */
@Slf4j
public class NettyTimeClient {

    private final int port;
    private final String host;

    public NettyTimeClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.remoteAddress(new InetSocketAddress(host, port));
            bootstrap.handler(new NettyClientHandler());

            ChannelFuture clientFuture = bootstrap.connect().sync();
            clientFuture.channel().closeFuture().sync();
        }catch (Exception e){

        }finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        try {
            for(int i=0;i<100;i++) {
                new NettyTimeClient(9999, "127.0.0.1").start();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
