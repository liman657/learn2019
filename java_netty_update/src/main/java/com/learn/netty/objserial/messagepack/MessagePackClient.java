package com.learn.netty.objserial.messagepack;

import com.learn.netty.objserial.ObjSerialClient;
import com.learn.netty.objserial.ObjSerialClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/9/23
 * comment:
 */
@Slf4j
public class MessagePackClient {

    public void connect(int port,String host){
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));
            bootstrap.option(ChannelOption.TCP_NODELAY,true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                    ch.pipeline().addLast("msgpack decode",new MsgpackDecoder());
                    ch.pipeline().addLast("msgpack encode",new MsgpackEncoder());
                    // 在MessagePack编码器之前增加LengthFieldPrepender，它将在ByteBuf之前增加2个字节的消息长度字段
                    ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                    ch.pipeline().addLast("msgpack handler",new MessagePackClientHandler());
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
        new MessagePackClient().connect(9090,"127.0.0.1");
    }
}
