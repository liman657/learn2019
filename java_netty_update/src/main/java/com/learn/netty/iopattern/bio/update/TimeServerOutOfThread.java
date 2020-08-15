package com.learn.netty.iopattern.bio.update;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * autor:liman
 * createtime:2020/8/11
 * comment:
 * 参照Netty权威指南一书，BIO实例
 */
@Slf4j
public class TimeServerOutOfThread {

    public static void main(String[] args) {

        int port = 8999;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            log.info("Time server start in port:{}", port);
            Socket socket = null;
            while (true) {
                socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                String currentTime = null;
                String body = null;
                body = in.readLine();
                if (body == null)
                    break;
                Thread.sleep(10000);
                log.info("this time server receive order:{}", body);
                if ("QUERY TIME".equalsIgnoreCase(body)) {
                    currentTime = LocalDateTime.now().toString();
                } else {
                    currentTime = "BAD ORDER";
                }
                out.println(currentTime);
            }

        } catch (Exception e) {
            log.error("异常信息为:{}", e);
        } finally {
            if (serverSocket != null) {
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