package com.learn.spring.websocket.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * autor:liman
 * createtime:2020/11/8
 * comment:websocket的服务端
 */
@Component
@ServerEndpoint("/websocket")

public class WebSocketServer {

    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String username;
    @OnOpen
    public void onOPen(@PathParam("username") String username, Session session) {
        this.username = username;
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        try {
            while(true){
                String currentTime=new Date().toString();
                sendMessage("当前时间为:"+currentTime);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
//            System.out.println(e);
        }
    }


    @OnMessage
    public void onMessage(String message, Session session) throws EncodeException, IOException {
        System.out.println("用户登录！"+message);
        System.out.println("当前在线人数！"+ onlineCount);
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnError
    public void onErroe(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public  void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    /**
     * 群发自定义消息
     * */
    public  void sendInfo(String message) throws IOException {
        for (WebSocketServer item : webSocketSet) {
            item.sendMessage(message);
        }
    }
    /**
     * 定向自定义消息
     * */
    public void sendSomeoneInfo(String message,String someone) throws IOException {
        for (WebSocketServer item : webSocketSet) {
            try {
                if(item.username.equals(someone)) {
                    item.sendMessage(message);
                    System.out.println("开始推送信息到："+someone);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
