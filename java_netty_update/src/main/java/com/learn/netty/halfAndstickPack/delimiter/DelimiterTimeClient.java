package com.learn.netty.halfAndstickPack.delimiter;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * autor:liman
 * createtime:2020/8/18
 * comment:
 */
@Slf4j
public class DelimiterTimeClient {

    private static final String delimiter_symbol = "@~";

    private final int port;
    private final String host;

    public DelimiterTimeClient(int port, String host) {
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
                    ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,Unpooled.copiedBuffer(delimiter_symbol.getBytes("UTF-8"))));
                    ch.pipeline().addLast(new DelimiterTimeClientHandler());
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
            new DelimiterTimeClient(9999, "127.0.0.1").start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
