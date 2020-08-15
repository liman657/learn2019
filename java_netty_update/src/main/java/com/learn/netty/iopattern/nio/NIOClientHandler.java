package com.learn.netty.iopattern.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * autor:liman
 * createtime:2020/8/9
 * comment:
 */
@Slf4j
public class NIOClientHandler implements Runnable{

    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;

    private volatile boolean started;

    public NIOClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            started = true;
        } catch (IOException e) {
            log.error("selector 获取channel 异常，异常信息为:{}",e);
        }
    }

    public void stop(){
        started = false;
    }

    @Override
    public void run() {
        try{
            doConnect();
        }catch (Exception e){
            log.error("处理连接异常，异常信息为:{}",e);
            System.exit(1);
        }
        while(started){
            try {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = keys.iterator();
                SelectionKey key = null;
                while(keyIterator.hasNext()){
                    key = keyIterator.next();
                    keyIterator.remove();
                    handleInput(key);//处理对应的key的事件
                }
            } catch (IOException e) {
                log.error("遍历selector出现异常，异常信息为：{}",e);
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if(key.isValid()){
            SocketChannel socketChannel = (SocketChannel) key.channel();
            if(key.isConnectable()){
                if(socketChannel.finishConnect()){

                }else{
                    System.exit(1);
                }
            }
            if(key.isReadable()){//变得可读
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(buffer);
                if(readBytes>0){
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String result = new String(bytes,"UTF-8");
                    log.info("客户端接收的消息:{}",result);
                }else if(readBytes<0){
                    key.channel();
                    socketChannel.close();
                }
            }
        }
    }

    /**
     * 往channel中写消息
     * @param channel
     * @param request
     * @throws IOException
     */
    private void doWrite(SocketChannel channel,String request) throws IOException {
        byte[] bytes = request.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        channel.write(writeBuffer);
    }

    private void doConnect() throws IOException{
        if(socketChannel.connect(new InetSocketAddress(host,port))){
            //建立连接，如果连接成功，则不需要做任何事情
        }else{
            //如果建立连接失败，则需要将当前的channel绑定到CONNECT事件
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
        }
    }

    /**
     * 发送消息给服务端
     * @param msg
     * @throws Exception
     */
    public void sendMsg(String msg) throws Exception {
        socketChannel.register(selector,SelectionKey.OP_READ);
        doWrite(socketChannel,msg);
    }
}
