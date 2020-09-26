package com.learn.netty.objserial;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;


/**
 * autor:liman
 * createtime:2020/9/21
 * comment: 对象序列化服务端
 */
@Slf4j
public class ObjSerialServer {

    private int port;

    public void start(int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG,100);
            bootstrap.localAddress(new InetSocketAddress("127.0.0.1",port));
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    //Netty提供的对象解码器，可以对类加载进行缓存
                    ch.pipeline().addLast(new ObjectDecoder(
                            1024*1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())
                    ));

                    //Netty提供的对象编码器
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjSerialServerHandler());
                }
            });
            ChannelFuture serverFuture = bootstrap.bind().sync();
            serverFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
            log.error("服务器出现异常，异常信息为:{}",e);
        }finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new ObjSerialServer().start(8989);
    }
}