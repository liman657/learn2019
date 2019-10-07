package com.learn.bio;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * autor:liman
 * createtime:2019/10/7
 * comment:
 */
@Slf4j
public class Server {

    private static int DEFAULT_PORT = 8888;

    private static ServerSocket serverSocket;

    public static void start() throws IOException {
        start(DEFAULT_PORT);
    }

    private synchronized static void start(int defaultPort) throws IOException {
        if (serverSocket != null) {
            return;
        }
        try {
            serverSocket = new ServerSocket(defaultPort);
            log.info("服务端顺利启动，端口为:{}", defaultPort);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerHandler(socket)).start();
            }
        } finally {
            //一些必要的清理工作
            if (serverSocket != null) {
                System.out.println("服务端已关闭。");
                serverSocket.close();
                serverSocket = null;
            }
        }
    }

}
