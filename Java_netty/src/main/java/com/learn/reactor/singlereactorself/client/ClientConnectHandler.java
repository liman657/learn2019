package com.learn.reactor.singlereactorself.client;


import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * autor:liman
 * createtime:2019/10/16
 * comment:
 */
public class ClientConnectHandler implements Runnable{

    private final SocketChannel socketChannel;
    private final Selector selector;

    public ClientConnectHandler(SocketChannel socketChannel, Selector selector) {
        this.selector = selector;
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try{
            if(socketChannel.finishConnect()){//与服务端的三次握手完成
                System.out.println(String.format("已完成 %s 的连接", socketChannel.getRemoteAddress()));

                new ClientSelfHandler(socketChannel,selector);
            }
        }catch (Exception e){

        }
    }
}
