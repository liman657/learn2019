package com.learn.netty.iopattern.bio.update;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * autor:liman
 * createtime:2020/8/11
 * comment:Time client的客户端
 */
@Slf4j
public class TimeClient {

    public static void main(String[] args) {
        int port = 8999;
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("QUERY TIME");
            log.info("send time:{},send order to server succeed",LocalDateTime.now().toString());
            String responseMessage = in.readLine();
            log.info("server time is :{}", responseMessage);
        } catch (Exception e) {

        }finally {
            if(out!=null){
                out.close();
            }
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
