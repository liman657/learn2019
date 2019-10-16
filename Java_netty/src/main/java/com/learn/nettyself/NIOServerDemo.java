package com.learn.nettyself;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * autor:liman
 * createtime:2019/10/9
 * comment: 自己写一遍NIOService
 */
@Slf4j
public class NIOServerDemo implements Runnable{

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 6777;
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    private Selector selector;

    public NIOServerDemo() {
        try{
            selector = Selector.open();//初始化一个selector
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();//初始化一个channel
            serverSocketChannel.configureBlocking(false);//开启非阻塞
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);//注册accept事件
            log.info("服务端启动（其实没有真正开始建立连接，只是注册到了selector上，惊不惊喜？）>>>>>>>>>>>>>>");
        }catch (Exception e){
            log.error("出现异常,异常信息为:{}",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        //这里要一个线程进行selector上的轮询
        while(true){
            try{
                int select = selector.select();
                if(select == 0){
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if(!key.isValid()){
                        continue;
                    }
                    if(key.isAcceptable()){//可以建立连接了
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
//                        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
                        SocketChannel socketChannel = serverSocketChannel.accept();//这个时候会阻塞，建立连接
                        log.info("与客户端的连接已经建立，可以开始通信了");
                        //也可以将SocketChannel注册到selector上
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ);
                        log.info("注册了客户端的channel到selector上");
                    }

                    if(key.isReadable()){//如果可读了
                        readBuffer.clear();
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        int count = socketChannel.read(readBuffer);
                        if(-1 == count){//如果没有数据
                            key.channel().close();
                            key.cancel();
                            return;
                        }
                        readBuffer.flip();//开启buffer的读模式
                        byte[] datas = new byte[readBuffer.remaining()];
                        System.arraycopy(readBuffer.array(), readBuffer.position(), datas, 0, readBuffer.remaining());
                        System.out.println("rececive client data:"+new String(datas,"UTF-8"));
                    }
                }

            }catch (Exception e){
                log.error("轮询出现异常，异常信息为:{}",e);
                throw new RuntimeException(e);
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        new Thread(new NIOServerDemo()).start();
//        Thread.sleep(50000L);
        Thread.currentThread().join();
    }
}
