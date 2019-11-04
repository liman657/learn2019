package com.learn.reactor.multiReactorMultiThread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
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
//    private final Selector selector;
//
//    private final ServerSocketChannel serverSocketChannel;
//
//    public ServerAcceptor(ServerSocketChannel serverSocketChannel, Selector selector) {
//        this.selector = selector;
//        this.serverSocketChannel = serverSocketChannel;
//    }
//
//
//    @Override
//    public void run() {
//        SocketChannel socketChannel;
//        try{
//            socketChannel = serverSocketChannel.accept();
//            if(socketChannel!=null){
//                log.info("收到来自{}的连接",socketChannel.getRemoteAddress());
//                //在构造函数中绑定业务处理类
//                new ServerHandler(socketChannel,selector);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    private final ServerSocketChannel serverSocketChannel;

    private final int coreNum = Runtime.getRuntime().availableProcessors()*2;//核心线程数一般是CPU*2

    private final Selector[] selectors = new Selector[coreNum];//多个selector加入
    private int next = 0;//轮询使用selector的下标索引

    private SubReactor[] reactors = new SubReactor[coreNum];

    private Thread[] threads = new Thread[coreNum];

    public ServerAcceptor(ServerSocketChannel serverSocketChannel) throws IOException {
        this.serverSocketChannel = serverSocketChannel;

        //线程初始化
        for(int i=0;i<coreNum;i++){
            selectors[i] = Selector.open();
            reactors[i]=new SubReactor(selectors[i],i);
            threads[i]=new Thread(reactors[i]);
            threads[i].start();//启动subReactor线程
        }
    }

    @Override
    public void run() {
        SocketChannel socketChannel;
        try{
            socketChannel = serverSocketChannel.accept();
            if(socketChannel!=null){
                log.info("收到来自{}的连接请求",socketChannel.getRemoteAddress());
                socketChannel.configureBlocking(false);
                reactors[next].setRegister(true);//这里不是很懂
                selectors[next].wakeup();//这里不是很懂
                SelectionKey selectionKey = socketChannel.register(selectors[next],SelectionKey.OP_READ);
                selectors[next].wakeup();//这里不是很懂
                reactors[next].setRegister(false);
                selectionKey.attach(new ServerHandler(socketChannel,selectors[next]));

                if(++next == selectors.length){
                    next=0;
                }
            }
        }catch (Exception e){
            log.error("监听异常,异常信息为:{}",e.fillInStackTrace());
        }
    }
}
