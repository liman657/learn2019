package com.learn.netty.reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

/**
 * autor:liman
 * createtime:2020/8/12
 * comment:
 */
@Slf4j
public class MultiplexerTimeServer implements Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            //注册到选择器的通道必须是非阻塞模式
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            //将serverSocketChannel注册到selector上
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("The time server is start in port : {}", port);
        } catch (IOException e) {
            log.error("服务启动出行异常，异常信息为:{}", e);
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> keysIterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (keysIterator.hasNext()) {
                    key = keysIterator.next();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        log.error("通道出现异常，异常信息为:{}",e);
                        if(key!=null){
                            key.cancel();
                            if(key.channel()!=null){
                                key.channel().close();
                            }
                        }
                    }
                    keysIterator.remove();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理Selector上的注册事件
     * @param key
     * @throws IOException
     */
    private void handleInput(SelectionKey key) throws IOException, InterruptedException {
        String currentTime = null;
        if (key.isValid()) {

            if(key.isAcceptable()){
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector,SelectionKey.OP_READ);
            }

            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    log.info("the time server receive order:{}", body);
                    if ("QUERY TIME".equalsIgnoreCase(body)) {
                        Thread.sleep(10000);
                        currentTime = LocalDateTime.now().toString();
                    } else {
                        currentTime = "BAD ORDER";
                    }
                    doWrite(socketChannel, currentTime);
                } else if (readBytes < 0) {
                    key.channel();

                }
                socketChannel.close();
            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
        }
    }
}
