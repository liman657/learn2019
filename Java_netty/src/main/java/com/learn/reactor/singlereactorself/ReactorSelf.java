package com.learn.reactor.singlereactorself;

import com.learn.reactor.singlereactormodel.Reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * autor:liman
 * createtime:2019/10/16
 * comment: 服务端
 */
public class ReactorSelf implements Runnable {

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    public ReactorSelf(int port) throws IOException {
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);

        SelectionKey acceptKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        acceptKey.attach(new AcceptorHandler(selector,serverSocketChannel));
    }

    //这里就做了分发的功能，如果是连接建立就分发给AcceptorHandler，如果是数据准备就绪，就分发给ServerDataHandler
    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                int select = selector.select();
                if(select<0){
                    return;
                }

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator iterator = keys.iterator();
                while(iterator.hasNext()){
                    dispatch((SelectionKey)iterator.next());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey key) {
        Runnable run = (Runnable)key.attachment();
        if(run!=null){
            run.run();
        }
    }

    public static void main(String[] args) {
        try {
            new Thread(new Reactor(8888)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
