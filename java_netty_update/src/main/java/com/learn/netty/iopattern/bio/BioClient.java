package com.learn.netty.iopattern.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import static com.learn.netty.config.BaseIOConfig.DEFAULT_SERVER_IP;
import static com.learn.netty.config.BaseIOConfig.DEFAULT_SERVER_PORT;

/**
 * autor:liman
 * createtime:2020/8/9
 * comment:BIO client
 */
@Slf4j
public class BioClient {

    public static void main(String[] args) throws IOException {
        Socket socket =new Socket(DEFAULT_SERVER_IP,DEFAULT_SERVER_PORT);
        log.info("------请输入请求消息------");
        new ReadMsg(socket).start();
        PrintWriter printWriter = null;
        while(true){
            printWriter = new PrintWriter(socket.getOutputStream());
//            log.info("准备发送的消息为:");
            printWriter.println(new Scanner(System.in).next());
            printWriter.flush();
        }

    }

    /**
     * 读取消息的线程类
     */
    private static class ReadMsg extends Thread{
        Socket socket;

        public ReadMsg(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try{
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = null;
                while((line=br.readLine())!=null){
//                    log.info("读取到的信息为:{}",line);
                    System.out.println("从服务器收到的信息:"+line);
                }
            }catch(SocketException socketException){
                log.error("网络建立出现异常，异常信息为:{}",socketException);
            }catch (Exception e){
                log.error("网络读取出现异常，异常信息为:{}",e);
            }finally {
                clean();
            }
        }

        /**
         * 关闭socket连接
         */
        private void clean(){
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    log.error("socket 关闭异常，异常信息为:{}",e);
                }
            }
        }
    }

}
