package com.learn.netty.component.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * autor:liman
 * createtime:2020/8/26
 * comment:ServerSocketChannel的实例
 */
@Slf4j
public class ServerSocketChannelSelf {

    public static void main(String[] args) {
        ServerSocketChannel  serverSocketChannel = null;
        SocketChannel socketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1",8899));

            socketChannel = serverSocketChannel.accept();
            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            writeBuffer.put("hello this is Server,I'm listening in port 8899".getBytes("UTF-8"));
            writeBuffer.flip();
            socketChannel.write(writeBuffer);

            //读取数据
            String messageFromClient = readInfoFromClient(socketChannel, readBuffer);
            log.info("从客户端读取到的信息为:{}",messageFromClient);
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socketChannel.close();
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readInfoFromClient(SocketChannel socketChannel,ByteBuffer buffer) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        socketChannel.read(buffer);
        buffer.flip();
        while(buffer.hasRemaining()){
            while(buffer.position()<buffer.limit()) {
                stringBuilder.append((char) buffer.get());
            }
        }
        return stringBuilder.toString();
    }

}
