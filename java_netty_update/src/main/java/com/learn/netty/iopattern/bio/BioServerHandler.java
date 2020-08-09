package com.learn.netty.iopattern.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * autor:liman
 * createtime:2020/8/9
 * comment:
 * 服务端的处理类
 */
@Slf4j
public class BioServerHandler implements Runnable{

    private Socket socket;

    public BioServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outPrinter = new PrintWriter(socket.getOutputStream(),true);
            String message;
            String result;
            while((message = br.readLine())!=null){
                log.info("server accept message : {}",message);
                result = responseMessage(message);
                outPrinter.println(result);
            }
        }catch (Exception e){
            log.error("500异常，异常信息为:{}",e);
        }finally {
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }

    private String responseMessage(String message){
        LocalDateTime localDateTime = LocalDateTime.now();
        return "hello "+message + " received server time is "+localDateTime.toString();
    }
}
