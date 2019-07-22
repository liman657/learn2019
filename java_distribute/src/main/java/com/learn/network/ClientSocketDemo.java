package com.learn.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * autor:liman
 * createtime:2019/7/22
 * comment:
 */
public class ClientSocketDemo {

    public static void main(String[] args) {
        try {
            //与服务器建立连接
            Socket socket = new Socket("localhost",9999);

            //获取socket对应的输入和输出流
            System.out.println("客户端开始获取数据流");
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //获取控制台的输入流
            BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));

            String data = sin.readLine();//获得控制台输入
            while(!data.equals("bye")){
                System.out.println("往服务器发送的数据为:"+data);
                out.println(data);//将数据发送到服务端
                System.out.println("从服务器接收到的数据为:"+in.readLine());
                data = sin.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
