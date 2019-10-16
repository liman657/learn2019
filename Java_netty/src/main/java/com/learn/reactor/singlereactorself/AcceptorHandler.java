package com.learn.reactor.singlereactorself;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;

/**
 * autor:liman
 * createtime:2019/10/16
 * comment:连接建立之后的处理函数
 */
public class AcceptorHandler implements Runnable{
    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    public AcceptorHandler(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        SocketChannel socketChannel;
        while(!Thread.interrupted()){
            try {
                socketChannel = serverSocketChannel.accept();
                if(socketChannel!=null){
                    System.out.println(String.format("收到来自%s的连接",socketChannel.getRemoteAddress()));
                    //开始建立连接后的处理
                    new ServerDataHandler(selector,socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
