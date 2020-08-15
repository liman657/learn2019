package com.learn.netty.iopattern.bio.update;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * autor:liman
 * createtime:2020/8/11
 * comment:
 * 参照Netty权威指南一书，BIO实例
 */
@Slf4j
public class TimeServer {

    public static void main(String[] args) {

        int port = 8999;
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            log.info("Time server start in port:{}",port);
            Socket socket = null;
            int threadCount=0;
//            socket = serverSocket.accept();
            while(true){
                socket = serverSocket.accept();
                new Thread(new TimeServerHandler(socket),"thread_name"+threadCount++).start();
            }

        }catch (Exception e){

        }finally {
            if(serverSocket!=null){
                log.info("the time server close!");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }



}
