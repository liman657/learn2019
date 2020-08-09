package com.learn.netty.iopattern.aio.client;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * autor:liman
 * createtime:2020/8/9
 * comment:
 */
@Slf4j
public class AioClientHandler implements CompletionHandler<Void,AioClientHandler>,Runnable {

    private String host;
    private int port;
    private AsynchronousSocketChannel clientChannel;
    private CountDownLatch latch;

    public AioClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            //创建一个实际类型的通道
            clientChannel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 操作系统的回调方法
     * @param result
     * @param attachment
     */
    @Override
    public void completed(Void result, AioClientHandler attachment) {
        log.info("已经连接到客户端");
    }

    @Override
    public void failed(Throwable exc, AioClientHandler attachment) {
        log.info("连接发生失败,异常信息为:{}",exc);
        latch.countDown();
        try {
            clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        clientChannel.connect(new InetSocketAddress(host,port),this,this);
        try {
            latch.await();
            clientChannel.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
