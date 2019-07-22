package com.learn.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * autor:liman
 * createtime:2019/7/22
 * comment:
 */
public class ServerSocketDemo {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try{
            serverSocket = new ServerSocket(9999);

            //开始阻塞监听
            Socket socket = serverSocket.accept();

            //通过socket拿到输入输出流
            System.out.println("服务端开始获取数据流");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            //拿到控制台数据
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("客户端发送过来的数据为："+in.readLine());
            String line = sin.readLine();//准备发送的数据
            while(!"bye".equals(line)){
                writer.println(line);//将数据发送到客户端
                writer.flush();

                //继续读取客户端发送过来的数据
                System.out.println("客户端发送过来的数据为:"+in.readLine());
                line = sin.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
