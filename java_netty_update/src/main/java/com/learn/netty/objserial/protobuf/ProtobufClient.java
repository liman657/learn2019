package com.learn.netty.objserial.protobuf;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/9/21
 * comment:序列化客户端
 */
@Slf4j
public class ProtobufClient {


    public void connect(int port,String host){
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());//同时也需要加一个protobuf报文长度的解码器
                    ch.pipeline().addLast(new ProtobufDecoder(UserEntityResponseProto.UserEntityResponse.getDefaultInstance()));//入站接受服务端消息的解码器
                    ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());//protobuf定长的编码器
                    ch.pipeline().addLast(new ProtobufEncoder());
                    ch.pipeline().addLast(new ProtobufSerialClientHandler());
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
        new ProtobufClient().connect(8989,"127.0.0.1");
    }

}
