package com.learn.netty.objserial;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;

/**
 * autor:liman
 * createtime:2020/9/21
 * comment:序列化客户端
 */
@Slf4j
public class ObjSerialClient {


    public void connect(int port,String host){
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    //Netty提供的对象解码器 ClassResolvers.cacheDisabled 禁止对类加载进行缓存
                    ch.pipeline().addLast(new ObjectDecoder(
                            1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())
                    ));

                    //Netty提供的对象编码器
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjSerialClientHandler());
                }
            });
            ChannelFuture clientFuture = bootstrap.connect(host,port).sync();
            clientFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
            log.error("服务器出现异常，异常信息为:{}",e);
        }finally {
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new ObjSerialClient().connect(8989,"127.0.0.1");
    }

}
