package com.learn.netty.objserial.messagepack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * autor:liman
 * createtime:2020/9/23
 * comment:
 */
@Slf4j
public class MessagePackService {

    private int port;

    public void start(int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workGroup);
            bootstrap.channel(NioServerSocketChannel.class);
//            bootstrap.option(ChannelOption.SO_BACKLOG,100);
            bootstrap.localAddress(new InetSocketAddress("127.0.0.1",port));
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                    ch.pipeline().addLast("msgpack decode",new MsgpackDecoder());
                    ch.pipeline().addLast("framelength encode",new LengthFieldPrepender(2));
                    ch.pipeline().addLast("msgpack encode",new MsgpackEncoder());
                    ch.pipeline().addLast("msgpack handler",new MessagePackServerHandler());
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
        new MessagePackService().start(9090);
    }

}
