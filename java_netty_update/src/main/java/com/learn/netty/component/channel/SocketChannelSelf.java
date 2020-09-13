package com.learn.netty.component.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * autor:liman
 * createtime:2020/8/26
 * comment:SocketChannel的实例
 */
@Slf4j
public class SocketChannelSelf {

    public static void main(String[] args) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1",8899));

            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);

            String clientMessage = "i am client ";
            writeBuffer.put(clientMessage.getBytes("UTF-8"));

            writeBuffer.flip();//变成读模式
            socketChannel.write(writeBuffer);

            readBuffer.clear();
            String serverInfo = readInfoFromBuffer(socketChannel,readBuffer);
            log.info("从服务端接受到的消息:{}",serverInfo);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从buffer读取相关内容
     * @param socketChannel
     * @param buffer
     * @return
     * @throws IOException
     */
    public static String readInfoFromBuffer(SocketChannel socketChannel,ByteBuffer buffer) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        socketChannel.read(buffer);//这里的buffer是可写模式，可以直接read
        buffer.flip();//将buffer变成可读模式
        while(buffer.hasRemaining()){
            while(buffer.position()<buffer.limit()) {
                stringBuilder.append((char) buffer.get());
            }
        }
        return stringBuilder.toString();
    }
}