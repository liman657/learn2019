package com.learn.tomcatSelf;

import com.learn.tomcatSelf.http.SelfRequest;
import com.learn.tomcatSelf.http.SelfResponse;
import com.learn.tomcatSelf.http.SelfServlet;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * autor:liman
 * createtime:2019/10/28
 * comment: 自己手写tomcat的主要入口类
 */
@Slf4j
public class SelfTomcat {

    //ServerSocket是tomcat的入口

    private int port = 8080;
    private ServerSocket serverSocket;
    private Map<String,SelfServlet> servletMapping = new HashMap<String,SelfServlet>();

    private Properties webXmlProperties = new Properties();

    /**
     * 读取web.xml配置文件，然后将得到的servlet放到容器，servletMapping中
     */
    private void init(){

        //加载web.xml文件,同时初始化 ServletMapping对象
        try{
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF + "web.properties");

            webXmlProperties.load(fis);

            for (Object k : webXmlProperties.keySet()) {

                String key = k.toString();
                if(key.endsWith(".url")){
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webXmlProperties.getProperty(key);
                    String className = webXmlProperties.getProperty(servletName + ".className");

                    //通常的servlet是单实例多线程的，（线程安全的）
                    SelfServlet obj = (SelfServlet)Class.forName(className).newInstance();
                    servletMapping.put(url, obj);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 自己tomcat的启动类
     */
    private void start(){
        init();//加载配置文件
        try{
            serverSocket = new ServerSocket(this.port);
            log.info("服务器启动，开启监听模式,监听端口:{}",port);
            while(true){//循环监听客户端请求
                Socket client = serverSocket.accept();
                process(client);//处理客户端的请求。
            }
        }catch (Exception e){
            log.error("服务器启动异常:{}",e.fillInStackTrace());
        }
    }

    /**
     * 处理客户端请求
     * @param client
     */
    private void process(Socket client) throws Exception{

        //拿到客户端的输入输出流
        InputStream inputStream = client.getInputStream();
        OutputStream outputStream = client.getOutputStream();

        SelfRequest request = new SelfRequest(inputStream);
        SelfResponse response = new SelfResponse(outputStream);

        String url = request.getUrl();//从客户端的HTTP字符串中获取url

        if(servletMapping.containsKey(url)){
            servletMapping.get(url).service(request,response);
        }else{//没找到，404
            response.write("404-Not Found");
//            throw new RuntimeException("404-Not Found");
        }

        outputStream.flush();
        outputStream.close();

        inputStream.close();
        client.close();
    }

    /**
     * 启动入口
     * @param args
     */
    public static void main(String[] args) {
        new SelfTomcat().start();
    }

}
