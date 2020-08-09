package com.learn.netty.iopattern.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.learn.netty.config.BaseIOConfig.DEFAULT_SERVER_PORT;

/**
 * autor:liman
 * createtime:2020/8/9
 * comment:BIO通信的server端
 */
@Slf4j
public class BioService {

    private static ServerSocket serverSocket;

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        try {
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void start() throws IOException {
        try{
            serverSocket = new ServerSocket(DEFAULT_SERVER_PORT);
            log.info("服务器已经启动，监听端口为:{}",DEFAULT_SERVER_PORT);
            while(true){
                Socket socket = serverSocket.accept();
                log.info("有新的客户端连接，IP地址为:{}",socket.getRemoteSocketAddress());
                executorService.execute(new BioServerHandler(socket));
            }
        }catch (Exception e){
            log.error("网络连接出现异常，异常信息为:{}",e);
        }finally {
            if(serverSocket!=null){
                serverSocket.close();
            }
        }
    }

}
