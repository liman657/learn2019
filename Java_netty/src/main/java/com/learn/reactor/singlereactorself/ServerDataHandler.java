package com.learn.reactor.singlereactorself;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * autor:liman
 * createtime:2019/10/16
 * comment:
 */
public class ServerDataHandler implements Runnable{

    private final Selector selector;
    private final SocketChannel socketChannel;
    private final SelectionKey selectionKey;

    private ByteBuffer readByteBuffer = ByteBuffer.allocate(2048);
    private ByteBuffer writeByteBuffer = ByteBuffer.allocate(1024);

    public ServerDataHandler(Selector selector, SocketChannel socketChannel) throws ClosedChannelException {
        this.selector = selector;
        this.socketChannel = socketChannel;

        selectionKey=socketChannel.register(selector,SelectionKey.OP_READ);
        selectionKey.attach(this);
//        selectionKey.interestOps()
    }


    @Override
    public void run() {
        dealEvent(selectionKey);
    }

    private void dealEvent(SelectionKey key) {
        try{
            if(key.isReadable()){
                read();
                return;
            }
            if(key.isWritable()){
                write();
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 输出数据
     */
    private void write() throws IOException {
        if(selectionKey.isValid()){

            int count = socketChannel.read(readByteBuffer);
            if(-1==count){
                System.out.println("未读取到有效信息");
            }
            readByteBuffer.flip();//开启读模式
            byte[] datas = new byte[readByteBuffer.remaining()];
            System.arraycopy(readByteBuffer.array(), readByteBuffer.position(), datas, 0, readByteBuffer.remaining());

            writeByteBuffer.clear();
            String result = String.format("我收到来自%s的信息,%s,200 OK ", socketChannel.getRemoteAddress(), new String(datas, "UTF-8"));

            writeByteBuffer.put(result.getBytes("UTF-8"));
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
                System.out.println("未读取到有效信息");
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
