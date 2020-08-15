package com.learn.netty.iopattern.bio.fakeAsynIo;

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
 * comment:
 */
@Slf4j
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(),true);
            String currentTime = null;
            String body = null;
            while(true){
                body = in.readLine();
                if(body == null)
                    break;
                Thread.sleep(10000);
                log.info("this time server receive order:{}",body);
                if("QUERY TIME".equalsIgnoreCase(body)){
                    currentTime = LocalDateTime.now().toString();
                }else{
                    currentTime = "BAD ORDER";
                }
                out.println(currentTime);
            }
        }catch (Exception e){
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if(out!=null){
                out.close();
            }

            if(this.socket!=null){
                try {
                    this.socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
