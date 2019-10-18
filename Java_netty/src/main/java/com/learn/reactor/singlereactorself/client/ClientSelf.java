package com.learn.reactor.singlereactorself.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * autor:liman
 * createtime:2019/10/16
 * comment:
 */
public class ClientSelf implements Runnable{

    private Selector selector;

    private SocketChannel socketChannel;

    public ClientSelf(String ip,int port) {
        try{
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(ip,port));

            SelectionKey key = socketChannel.register(selector, SelectionKey.OP_CONNECT);
            key.attach(new ClientConnectHandler(socketChannel,selector));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()){
                int select = selector.select();
                if(select<=0){
                    return;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator iterator = keys.iterator();
                while(iterator.hasNext()){
                    SelectionKey key = (SelectionKey) iterator.next();
                    dispatch(key);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey key) {
        Runnable run = (Runnable)key.attachment();
        if(run!=null){
            new Thread(run).start();
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new ClientSelf("127.0.0.1", 8888)).start();
        new Thread(new ClientSelf("127.0.0.1", 8888)).start();
        System.in.read();
    }
}
