package com.learn.reactor.multiReactorMultiThread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * autor:liman
 * createtime:2019/10/15
 * comment:
 */
@Slf4j
public class Connector implements Runnable{
    private final Selector selector;

    private final SocketChannel socketChannel;

    Connector(SocketChannel socketChannel, Selector selector) {
        this.socketChannel = socketChannel;
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            if (socketChannel.finishConnect()) { //这里连接完成（与服务端的三次握手完成）
                log.info("已经完成{}的连接",socketChannel.getRemoteAddress());
                new ClientHandler(socketChannel, selector); //连接建立完成后，接下来的动作交给Handler去处理（读写等）
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
