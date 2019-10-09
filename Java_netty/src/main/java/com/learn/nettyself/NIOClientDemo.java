package com.learn.nettyself;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * autor:liman
 * createtime:2019/10/9
 * comment:
 */
@Slf4j
public class NIOClientDemo implements Runnable {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 6777;
//    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    private static void clientRequest() {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_ADDRESS, PORT);
        SocketChannel socketChannel = null;

        try {
            socketChannel = SocketChannel.open();//初始化一个客户端的channel
            socketChannel.configureBlocking(false);
            log.info("开始与服务端建立连接");
            socketChannel.connect(inetSocketAddress);
            if (socketChannel.isConnectionPending()) {
                socketChannel.finishConnect();
            }
            log.info("连接成功");
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            String content = "hello this is nio client";
            readBuffer.put(content.getBytes("UTF-8"));
            readBuffer.flip();
            socketChannel.write(readBuffer);
            readBuffer.clear();
        } catch (Exception e) {
            log.error("系统异常，异常信息为:{}", e);
        } finally {
            if (null != socketChannel) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new NIOClientDemo()).start();
    }

    @Override
    public void run() {
        clientRequest();
    }
}
