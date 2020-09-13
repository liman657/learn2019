package com.learn.netty.reactor.singlethread;

import lombok.extern.slf4j.Slf4j;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * autor:liman
 * createtime:2020/9/9
 * comment: Acceptor的处理类 处理连接的建立
 */
@Slf4j
public class AcceptorHandler implements IHandler{

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private AtomicInteger totalCount = new AtomicInteger(0);

    public AcceptorHandler(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void handlerSelectKey() {
        try{
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel!=null){
                log.info("当前总连接数:{}",totalCount.incrementAndGet());
                new KeyEventHandler(selector,socketChannel);
            }
        }catch (Exception e){
            log.error("事件分发异常，异常信息为:{}",e);
        }
    }
}
