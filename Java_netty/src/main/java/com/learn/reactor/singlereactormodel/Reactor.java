package com.learn.reactor.singlereactormodel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * autor:liman
 * createtime:2019/10/15
 * comment:单Reactor线程模型的服务端实现
 * Reactor——负责监听就绪事件，并对事件进行分发处理
 */
public class Reactor implements Runnable{

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);

        //将ServerSocketChannel注册到selector上，订阅ACCEPT事件
        SelectionKey keys = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

        //绑定连接建立的处理对象
        keys.attach(new ServerAcceptor(serverSocketChannel,selector));
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                selector.select();//就绪事件到达之前阻塞
                Set selected = selector.selectedKeys();
                Iterator iterator = selected.iterator();
                while(iterator.hasNext()){
                    //进行任务的分发
                    dispatch((SelectionKey)iterator.next());
                }
                selected.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey key) {
        //这里并没有做区分，任务的分发依旧交给了服务器主线程去处理。
        Runnable runThread = (Runnable) key.attachment();
        if(runThread!=null){
            runThread.run();
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(8888)).start();
    }
}
