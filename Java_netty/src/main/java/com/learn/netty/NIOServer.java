package com.learn.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * autor:liman
 * createtime:2019/10/5
 * comment:
 * NIO 即Non-block IO，非阻塞同步通信方式
 * 从BIO的阻塞到NIO的非阻塞，这是一大进步，归功于Buffer，Channel，Selector三个设计实现
 * Buffer：缓冲区。NIO的数据操作都是在缓冲区中进行。
 * 缓冲区实际上是一个数组，而BIO是将数据直接写入或读取到stream对象。
 * Channel：通道。NIO可以通过Channel进行数据的读，写和同时读写操作。
 * Selector：多路复用器。NIO编程的基础。多路复用器提供选择已经就绪状态任务的能力。
 */
public class NIOServer implements Runnable {
    private final int BUFFER_SIZE = 1024; // 缓冲区大小
    private final int PORT = 8888;        // 监听的端口
    private Selector selector;            // 多路复用器，NIO编程的基础，负责管理通道Channel
    // 缓冲区Buffer，和BIO的一个重要区别（NIO读写数据是在缓冲区中进行，而BIO是通过流的形式）
    private ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    public NIOServer() {
        try {
            // 1.开启多路复用
            selector = Selector.open();
            // 2.打开服务器通道（网络读写通道）即开启channel
            ServerSocketChannel channel = ServerSocketChannel.open();
            // 3.设置服务器通道为非阻塞模式，true为阻塞模式，false为非阻塞模式。
            // 将channel设置成非阻塞模式
            channel.configureBlocking(false);
            // 4.绑定端口
            channel.socket().bind(new InetSocketAddress(PORT));

            /**
             * SelectionKey.OP_READ : 表示关注读数据就绪事件
             * SelectionKey.OP_WRITE : 表示关注写数据就绪事件
             * SelectionKey.OP_CONNECT : 表示关注socket channel的连接完成事件
             * SelectionKey.OP_ACCEPT : 表示关注server-socket channel的accept事件
             */
            channel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server start >>>>>>>> port : " + PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 需要一个线程负责Selector的轮询
    public void run() {
        while (true) {
            try {
                //1.多路复用监听器阻塞
                selector.select();
                //2.多路复用器已经选择结果集
                Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
                //3.不停的轮询
                while (selectionKeys.hasNext()) {
                    //4.获取其中一个key
                    SelectionKey key = selectionKeys.next();
                    //5.获取后从容器中移出
                    selectionKeys.remove();
                    //6.只获取有效的key
                    if (!key.isValid()) {
                        continue;
                    }
                    //阻塞状态处理
                    if (key.isAcceptable()) {
                        accept(key);
                    }
                    //可读状态处理
                    if (key.isReadable()) {
                        read(key);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置阻塞，等待client请求。
     * 在传统IO中，用的是ServerSocket和Socket。
     * 在NIO中用的是在NIO中采用的ServerSocketChannel和SocketChannel。
     *
     * @param selectionKey
     */
    private void accept(SelectionKey selectionKey) {

        try {
            //1.获取通道服务
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            //2.执行阻塞方法
            SocketChannel socketChannel = serverSocketChannel.accept();
            //3.设置服务器通道为非阻塞模式，false为非阻塞模式
            socketChannel.configureBlocking(false);
            //4.把通道注册到多路复用器上，并设置读取标识
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param selectionKey
     */
    private void read(SelectionKey selectionKey) {
        try {
            //1.清空缓冲区数据
            readBuffer.clear();
            //2.获取在多路复用器上注册的通道
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            //3.读取数据，返回
            int count = socketChannel.read(readBuffer);
            //4.返回内容为-1，表示没有数据
            if(-1 == count){
                selectionKey.channel().close();
                selectionKey.cancel();
                return;
            }
            //5.有数据则在读取数据前进行复位操作
            readBuffer.flip();
            //6.根据缓冲区大小创建一个相应大小的bytes数组，用来获取值
            byte[] bytes = new byte[readBuffer.remaining()];
            //7.接受缓冲区数据
            readBuffer.get(bytes);
            //8.打印获取到的数据
            System.out.println("NIO Server : "+new String(bytes,"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Thread(new NIOServer()).start();
    }
}