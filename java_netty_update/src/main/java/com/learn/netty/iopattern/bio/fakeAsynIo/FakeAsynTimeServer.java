package com.learn.netty.iopattern.bio.fakeAsynIo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * autor:liman
 * createtime:2020/8/11
 * comment:伪异步io的服务端
 */
@Slf4j
public class FakeAsynTimeServer {

    public static void main(String[] args) {
        int fakeAsynPort = 9909;

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(fakeAsynPort);
            log.info("fake asyn time server start in port : {}",fakeAsynPort);
            Socket socket = null;
            TimeServerHandlerThreadPool timeThreadPool = new TimeServerHandlerThreadPool(50,1000);
            while(true){
                //对比多线程的方式，这里只是采用了线程池而已，没什么可说的
                socket = serverSocket.accept();
                timeThreadPool.execute(new TimeServerHandler(socket));
            }
        }catch (Exception e){

        }finally {
            if(serverSocket!=null){
                log.info("time server close");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverSocket = null;
            }
        }
    }

}
