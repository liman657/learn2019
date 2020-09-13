package com.learn.netty.reactor.singlethread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;

/**
 * autor:liman
 * createtime:2020/9/10
 * comment:
 */
@Slf4j
public class KeyEventHandler implements IHandler {

    private Selector selector;
    private SocketChannel socketChannel;
    private SelectionKey selectionKey;

    /**
     * 这里需要绑定非连接事件的处理器
     * @param selector
     * @param socketChannel
     */
    public KeyEventHandler(Selector selector, SocketChannel socketChannel) {
        try {
            this.selector = selector;
            this.socketChannel = socketChannel;
            this.socketChannel.configureBlocking(false);
            selectionKey = this.socketChannel.register(this.selector, 0);//这里没有注册任何事件到selector上，只是获取指定的选择键
            selectionKey.attach(this);
            //通过selectionKey注册可读事件
            selectionKey.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handlerSelectKey() {
        try {
            String currentTime = "";
            if(selectionKey.isValid()) {
                if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int readBytes = socketChannel.read(readBuffer);
                    if (readBytes > 0) {
                        readBuffer.flip();
                        byte[] bytes = new byte[readBuffer.remaining()];
                        readBuffer.get(bytes);
                        String body = new String(bytes, "UTF-8");
                        log.info("the time server receive order:{}", body);
                    }else if(readBytes<0){//如果读取不到数据，则表示SocketChannel关闭，直接返回
                        socketChannel.close();
                        return;
                    }
                    selectionKey.interestOps(SelectionKey.OP_WRITE);
                } else if (selectionKey.isWritable()) {
                    currentTime = LocalDateTime.now().toString();
                    log.info("send message 2 client ,current time is : {}",currentTime);
                    doWrite(socketChannel, currentTime);
                    selectionKey.interestOps(SelectionKey.OP_READ);
                }
                selectionKey.attach(this);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 通过缓冲区，往channel中写入数据
     * @param socketChannel
     * @param response
     * @throws IOException
     */
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
