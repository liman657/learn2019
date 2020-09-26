package com.learn.netty.halfAndstickPack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * autor:liman
 * createtime:2020/8/18
 * comment:
 */
@Slf4j
public class NettyHalfAndStickPackTimeClient {

    private final int port;
    private final String host;

    public NettyHalfAndStickPackTimeClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.TCP_NODELAY,true);
            bootstrap.remoteAddress(new InetSocketAddress(host, port));
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
//                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new NettyHalfAndStickPackTimeClientHandler());
                }
            });

            ChannelFuture clientFuture = bootstrap.connect().sync();
            clientFuture.channel().closeFuture().sync();
        } catch (Exception e) {

        } finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        try {
            new NettyHalfAndStickPackTimeClient(9999, "127.0.0.1").start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
