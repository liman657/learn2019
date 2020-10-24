package com.learn.netty.objserial.protobuf;

import com.learn.netty.objserial.ObjSerialServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
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
public class ProtoSerialServer {

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
                    ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());//protobuf数据包长度的解码器
                    ch.pipeline().addLast(new ProtobufDecoder(UserEntityRequestProto.UserEntityRequest.getDefaultInstance()));//客户端请求对象的解码器
                    ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());//出站消息的定长编码器
                    ch.pipeline().addLast(new ProtobufEncoder());//protobuf提供的编码器
                    ch.pipeline().addLast(new ProtobufSerialServerHandler());
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
        new ProtoSerialServer().start(8989);
    }
}