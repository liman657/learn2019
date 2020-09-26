package com.learn.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * autor:liman
 * createtime:2020/9/20
 * comment:用Netty实现一个Http服务器
 */
@Slf4j
public class NettyHttpServer {

    private int port = 8080;

    public NettyHttpServer(int port) {
        this.port = port;
    }

    public void start(){
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.localAddress(new InetSocketAddress(port));
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("encode",new HttpResponseEncoder());//Http返回消息的编码器
                    ch.pipeline().addLast("decode",new HttpRequestDecoder());//Http请求消息的解码器
                    ch.pipeline().addLast("aggres",new HttpObjectAggregator(10*1024*1024));//Http请求长度的限定
                    ch.pipeline().addLast("compressor",new HttpContentCompressor());
                    ch.pipeline().addLast("busHandler",new HttpBusHandler());//自定义逻辑处理器
                }
            });

            ChannelFuture serverFuture = serverBootstrap.bind().sync();
            serverFuture.channel().closeFuture().sync();
        }catch (Exception e){
            log.error("服务器出现异常，异常信息为:{}",e);
        }

    }

    public static void main(String[] args) {
        new NettyHttpServer(8080).start();
    }

}
