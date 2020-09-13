package com.learn.netty.reactor.singlethread;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * autor:liman
 * createtime:2020/8/12
 * comment:单线程reactor 模式的客户端
 */
@Slf4j
public class TimeReactorServer {

    private static final String host = "127.0.0.1";
    private static final int port = 9898;

    public static void main(String[] args) {
        //启动的时候，将服务端的ServerSocketChannel注册到Selector上，并绑定连接事件的处理方式
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            InetSocketAddress inetAddress =
                    new InetSocketAddress(host,
                            port);
            serverSocketChannel.bind(inetAddress);
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //绑定连接事件的处理Handler
            selectionKey.attach(new AcceptorHandler(selector,serverSocketChannel));

            //开启Reactor线程
            new Thread(new DispatchThread(selector)).start();

//            while(true){
//                selector.select(1000);
//                Set<SelectionKey> selected = selector.selectedKeys();
//                Iterator<SelectionKey> it = selected.iterator();
//                while (it.hasNext()) {
//                    //Reactor负责dispatch收到的事件
//                    SelectionKey sk = it.next();
//                    dispatch(sk);
//                }
//                selected.clear();
//            }
        } catch (Exception e) {
            log.error("连接建立出现异常，异常信息为:{}",e);
        }
    }

//    private static void dispatch(SelectionKey selectionKey){
//        IHandler handler = (IHandler) selectionKey.attachment();
//        //调用之前attach绑定到选择键的handler处理器对象
//        if (handler != null) {
//            handler.handlerSelectKey();
//        }
//    }
}
