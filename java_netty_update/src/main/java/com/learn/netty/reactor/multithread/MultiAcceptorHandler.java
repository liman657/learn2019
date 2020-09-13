package com.learn.netty.reactor.multithread;

import lombok.extern.slf4j.Slf4j;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * autor:liman
 * createtime:2020/9/9
 * comment: 多线程的Acceptor的处理器
 */
@Slf4j
public class MultiAcceptorHandler implements IMultiHandler {

    private Selector[] selectors;
    private ServerSocketChannel serverSocketChannel;
    private int selectorCount;
    private AtomicInteger selectorIndex = new AtomicInteger(0);

    private AtomicInteger totalConnectCount = new AtomicInteger(0);

    static ExecutorService pool = Executors.newFixedThreadPool(4);

    public MultiAcceptorHandler(Selector[] selectors, ServerSocketChannel serverSocketChannel,int selectorCount) {
        this.selectors = selectors;
        this.serverSocketChannel = serverSocketChannel;
        this.selectorCount = selectorCount;
    }

    @Override
    public void handlerSelectKey() {
        if(selectorIndex.incrementAndGet() == selectorCount){
            selectorIndex.set(0);
        }
        try{
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel!=null){
                log.info("总共建立的连接事件个数：{}",totalConnectCount.incrementAndGet());
                //这里将IO读写处理的Handler交给线程池去处理
                MultiKeyEventHandler eventHandler = new MultiKeyEventHandler(this.selectors[selectorIndex.get()], socketChannel);
                pool.execute(eventHandler);
            }
        }catch (Exception e){
            log.error("事件分发异常，异常信息为:{}",e);
        }
    }
}
