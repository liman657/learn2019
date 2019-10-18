package com.learn.reactor.singlereactorself.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * autor:liman
 * createtime:2019/10/16
 * comment:
 */
public class ClientSelfHandler implements Runnable {

    private final SocketChannel socketChannel;
    private final SelectionKey selectionKey;

    private ByteBuffer readByteBuffer = ByteBuffer.allocate(2048);
    private ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024);

    private AtomicInteger counter = new AtomicInteger();

    public ClientSelfHandler(SocketChannel socketChannel, Selector selector) throws IOException {

        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        this.selectionKey = socketChannel.register(selector,SelectionKey.OP_WRITE);
        this.selectionKey.attach(this);
//        selectionKey.interestOps();
        selector.wakeup();//唤起selector线程

    }

    @Override
    public void run() {
        try {
            send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dealEvent(selectionKey);
    }

    private void dealEvent(SelectionKey key) {
        try{
            if(key.isReadable()){
                read();
                return;
            }
            if(key.isWritable()){
                send();
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 输出数据
     */
    private void send() throws IOException {
        if(selectionKey.isValid()){

//            int count = socketChannel.read(readByteBuffer);
////            if(-1==count){
////                System.out.println("未读取到有效信息");
////            }
            int index = counter.incrementAndGet();
            if(index<=10){
                writeByteBuffer.clear();
                writeByteBuffer.put(String.format("客户端发送的第%s条消息",index).getBytes());
//                String result = String.format("客户端收到来自%s的信息,%s,200 OK ", socketChannel.getRemoteAddress(), new String(datas, "UTF-8"));

//                writeByteBuffer.put(result.getBytes("UTF-8"));
                writeByteBuffer.flip();//切换到读模式，用于通道读到buffer里的数据
                socketChannel.write(writeByteBuffer);
            }
//            readByteBuffer.flip();//开启读模式
//            byte[] datas = new byte[readByteBuffer.remaining()];
//            System.arraycopy(readByteBuffer.array(), readByteBuffer.position(), datas, 0, readByteBuffer.remaining());

//            writeByteBuffer.clear();
//            String result = String.format("客户端收到来自%s的信息,%s,200 OK ", socketChannel.getRemoteAddress(), new String(datas, "UTF-8"));

//            writeByteBuffer.put(result.getBytes("UTF-8"));
            selectionKey.cancel();
            socketChannel.close();
        }
    }

    /**
     * 读取数据
     */
    private void read() throws IOException {
        if(selectionKey.isValid()){
            readByteBuffer.clear();

            int count = socketChannel.read(readByteBuffer);
            if(-1==count){
                System.out.println("客户端未读取到有效信息");
            }
            readByteBuffer.flip();//开启读模式
            byte[] datas = new byte[readByteBuffer.remaining()];
            System.arraycopy(readByteBuffer.array(), readByteBuffer.position(), datas, 0, readByteBuffer.remaining());

            selectionKey.cancel();
            socketChannel.close();
            System.out.println("连接关闭");
        }
    }
}
