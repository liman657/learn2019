package com.learn.reactor.singlereactormodel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * autor:liman
 * createtime:2019/10/15
 * comment:
 */
@Slf4j
public class ServerHandler implements Runnable {

    private final SelectionKey selectionKey;
    private final SocketChannel socketChannel;

    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(2048);

    private final static int READ = 0;
    private final static int SEND = 1;

    private int status = READ;

    public ServerHandler(SocketChannel socketChannel, Selector selector) throws IOException {

        this.socketChannel = socketChannel;

        this.socketChannel.configureBlocking(false);
        selectionKey = socketChannel.register(selector, 0);
        selectionKey.attach(this);//将读写的处理类绑定为当前的业务类。
        selectionKey.interestOps(SelectionKey.OP_READ);
//        selector.wakeup();
    }


    @Override
    public void run() {
        try {
            switch (status) {
                case READ:
                    read();
                    break;
                case SEND:
                    send();
                    break;
                default:
            }
        } catch (IOException e) { //这里的异常处理是做了汇总，常出的异常就是server端还有未读/写完的客户端消息，客户端就主动断开连接，这种情况下是不会触发返回-1的，这样下面read和write方法里的cancel和close就都无法触发，这样会导致死循环异常（read/write处理失败，事件又未被cancel，因此会不断的被select到，不断的报异常）
            System.err.println("read或send时发生异常！异常信息：" + e.getMessage());
            selectionKey.cancel();
            try {
                socketChannel.close();
            } catch (IOException e2) {
                System.err.println("关闭通道时发生异常！异常信息：" + e2.getMessage());
                e2.printStackTrace();
            }
        }
    }

    private void read() throws IOException {
        if (selectionKey.isValid()) {
            readBuffer.clear();
            int count = socketChannel.read(readBuffer); //read方法结束，意味着本次"读就绪"变为"读完毕"，标记着一次就绪事件的结束
            if (count > 0) {
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("收到来自客户端的信息,信息为:{}", BufferUtils.readByteBufferInfo(readBuffer));
                status = SEND;
                selectionKey.interestOps(SelectionKey.OP_WRITE); //注册写方法
            } else {
                //读模式下拿到的值是-1，说明客户端已经断开连接，那么将对应的selectKey从selector里清除，否则下次还会select到，因为断开连接意味着读就绪不会变成读完毕，也不cancel，下次select会不停收到该事件
                //所以在这种场景下，（服务器程序）你需要关闭socketChannel并且取消key，最好是退出当前函数。注意，这个时候服务端要是继续使用该socketChannel进行读操作的话，就会抛出“远程主机强迫关闭一个现有的连接”的IO异常。
                selectionKey.cancel();
                socketChannel.close();
                System.out.println("read时-------连接关闭");
            }
        }
    }

    private void send() throws IOException {
        if (selectionKey.isValid()) {
            writeBuffer.clear();
            String returnMessage = String.format("服务端收到来自%s的信息：%s,  200ok;",
                    socketChannel.getRemoteAddress(),
                    new String(readBuffer.array()));
            writeBuffer.put(returnMessage.getBytes());
            log.info("服务端发送消息,消息为:{}", returnMessage);
            writeBuffer.flip();
            int count = socketChannel.write(writeBuffer); //write方法结束，意味着本次写就绪变为写完毕，标记着一次事件的结束
            if (count < 0) {
                //同上，write场景下，取到-1，也意味着客户端断开连接
                selectionKey.cancel();
                socketChannel.close();
                log.info("发送消息时连接关闭");
                System.out.println("send时-------连接关闭");
            }

            //没断开连接，则再次切换到读
            status = READ;
            selectionKey.interestOps(SelectionKey.OP_READ);
        }
    }
}
