package com.learn.nettyself;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * autor:liman
 * createtime:2019/10/9
 * comment: 自己写一遍NIOService
 */
@Slf4j
public class NIOServerDemoUpdate{
    
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    
    public NIOServerDemoUpdate() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(6777));
        selector = Selector.open();
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        log.info("服务端启动（其实没有真正开始建立连接，只是注册到了selector上，惊不惊喜？）>>>>>>>>>>>>>>");
        
        handleKeys();
    }

    private void handleKeys() throws IOException {
        while(true){
            int selectNums = selector.select();
            if(selectNums == 0){//如果没有事件准备好
                continue;
            }
            log.info("当前注册在selector上的channel数量为:{}",selectNums);
            
            //开始遍历selector上注册的channel
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if(!key.isValid()){
                    continue;
                }
                handleKey(key);
            }
        }
    }

    private void handleKey(SelectionKey key) throws ClosedChannelException {
        if(key.isAcceptable()){
            try {
                handleAcceptableKey(key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(key.isReadable()){
            handleReadableKey(key);
        }
        if(key.isWritable()){
            handleWriteable(key);
        }
    }

    private void handleWriteable(SelectionKey key) throws ClosedChannelException {
        // Client Socket Channel
        SocketChannel clientSocketChannel = (SocketChannel) key.channel();

        // 遍历响应队列
        List<String> responseQueue = (ArrayList<String>) key.attachment();
        for (String content : responseQueue) {
            // 打印数据
            System.out.println("写入数据：" + content);
            // 返回
            CodecUtil.write(clientSocketChannel, content);
        }
        responseQueue.clear();

        // 注册 Client Socket Channel 到 Selector
        clientSocketChannel.register(selector, SelectionKey.OP_READ, responseQueue);
    }

    /**
     * 处理可读事件
     * @param key
     */
    private void handleReadableKey(SelectionKey key) throws ClosedChannelException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer readBuffer = CodecUtil.read(socketChannel);

        if(readBuffer==null){
            log.info("断开channel");
            socketChannel.register(selector,0);
            return;
        }

        // 打印数据
        if (readBuffer.position() > 0) { // 写入模式下，
            String content = CodecUtil.newString(readBuffer);
            System.out.println("读取数据：" + content);

            // 添加到响应队列
            List<String> responseQueue = (ArrayList<String>) key.attachment();
            responseQueue.add("响应：" + content);
            // 注册 Client Socket Channel 到 Selector
            socketChannel.register(selector, SelectionKey.OP_WRITE, key.attachment());
        }
    }

    /**
     * 处理连接建立事件
     * @param key
     */
    private void handleAcceptableKey(SelectionKey key) throws IOException {
        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
        socketChannel.configureBlocking(false);
        log.info("接收到新的客户端channel，将其注册到selector上");
        socketChannel.register(selector,SelectionKey.OP_READ,new ArrayList<String>());
    }

    public static void main(String[] args) throws IOException {
        NIOServerDemoUpdate server = new NIOServerDemoUpdate();
    }

}
