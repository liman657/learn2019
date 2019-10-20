package com.learn.reactor.singlereactormodel;

import lombok.extern.slf4j.Slf4j;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * autor:liman
 * createtime:2019/10/15
 * comment:
 */
@Slf4j
public class ServerAcceptor implements Runnable{

    private final Selector selector;

    private final ServerSocketChannel serverSocketChannel;

    public ServerAcceptor(ServerSocketChannel serverSocketChannel, Selector selector) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }


    @Override
    public void run() {
        SocketChannel socketChannel;
        try{
            socketChannel = serverSocketChannel.accept();
            if(socketChannel!=null){
                log.info("收到来自{}的连接",socketChannel.getRemoteAddress());
                //在构造函数中绑定业务处理类
                new ServerHandler(socketChannel,selector);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
