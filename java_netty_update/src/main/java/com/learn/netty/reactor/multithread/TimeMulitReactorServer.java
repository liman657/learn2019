package com.learn.netty.reactor.multithread;

import com.learn.netty.reactor.singlethread.AcceptorHandler;
import com.learn.netty.reactor.singlethread.DispatchThread;
import com.learn.netty.reactor.singlethread.IHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Random;

/**
 * autor:liman
 * createtime:2020/8/12
 * comment:单线程reactor 模式的客户端
 */
@Slf4j
public class TimeMulitReactorServer {

    private static final String host = "127.0.0.1";
    private static final int port = 10229;
    private static final int selectorCount = 2;

    public static void main(String[] args) {
        //启动的时候，将服务端的ServerSocketChannel注册到Selector上，并绑定连接事件的处理方式
        try {
            Selector[] selectors = new Selector[selectorCount];
            for(int i = 0;i<selectors.length;i++){
                selectors[i]=Selector.open();
            }
            //开启Reactor线程，可以先开启子线程，反正这个时候也没有注册事件需要分配，让其空载就行
            //后面的连接事件绑定到指定的处理器上，相应的线程会自动轮询处理
            for(int i = 0;i<selectors.length;i++) {
                new Thread(new MultiDispatchThread(selectors[i]),"dispatchThread_"+i).start();
            }

            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            InetSocketAddress inetAddress =
                    new InetSocketAddress(host,
                            port);
            serverSocketChannel.bind(inetAddress);

            //按理说，这里随便注册一个selector都可以
            int index = new Random().nextInt(selectorCount);
            SelectionKey selectionKey = serverSocketChannel.register(selectors[index], SelectionKey.OP_ACCEPT);
            //绑定连接事件的处理Handler
            selectionKey.attach( new MultiAcceptorHandler(selectors, serverSocketChannel, selectorCount));
        } catch (Exception e) {
            log.error("连接建立出现异常，异常信息为:{}",e);
        }
    }
}
