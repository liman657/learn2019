package com.learn.netty.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * autor:liman
 * createtime:2020/8/17
 * comment:Netty的时间回显服务器
 */
@Slf4j
public class NettyTimeServer {

    private final int port;

    public NettyTimeServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        final NettyTimeServerHandler serverHandler = new NettyTimeServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.localAddress(new InetSocketAddress(port));

            //服务端接收到连接请求，需要新开启一个channel通信（socket），每一个channel需要有自己的事件处理的handler
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(serverHandler);
                }
            });
            //绑定到端口，阻塞等待直到连接完成
            ChannelFuture serverFuture =serverBootstrap.bind().sync();
            //阻塞，直到channel关闭
            serverFuture.channel().closeFuture().sync();

        }catch (Exception e){
//            group.shutdownGracefully().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }

    public void bind(int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.option(ChannelOption.SO_BACKLOG,1024);
            serverBootstrap.childHandler(new ChildChannelHandler());
        }catch (Exception e){

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new NettyTimeServerHandler());
        }
    }

    public static void main(String[] args) {
        int port = 9999;
        try {
            log.info("服务端启动，监听端口为:{}",port);
            new NettyTimeServer(port).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
