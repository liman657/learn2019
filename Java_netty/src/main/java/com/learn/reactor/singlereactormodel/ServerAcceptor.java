package com.learn.reactor.singlereactormodel;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * autor:liman
 * createtime:2019/10/15
 * comment:
 */
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
                System.out.println(String.format("收到来自%s的连接",socketChannel.getRemoteAddress()));
                new ServerHandler(socketChannel,selector);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
