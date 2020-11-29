package com.learn.spring.websocket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Random;

/**
 *@author
 *
 *
 *类说明：贵金属期货的实现
 */
@Controller
public class NobleMetalController {

    private static Logger logger = LoggerFactory.getLogger(NobleMetalController.class);

    @RequestMapping("/nobleMetal")
    public String stock(){
        return "nobleMetal";
    }

    @RequestMapping(value="/needPrice"
            ,produces="text/event-stream;charset=UTF-8"
            )
    @ResponseBody
    public String push(){
        Random r = new Random();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return makeResp(r);

    }

    /*业务方法，生成贵金属的实时价格*/
    private String makeResp(Random r){
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("retry:2000\n")
                .append("data:")
                .append(r.nextInt(100)+50+",")
                .append(r.nextInt(40)+35)
                .append("\n\n");
        return stringBuilder.toString();
    }








    /*------------以下为正确使用SSE的姿势------------------*/
    @RequestMapping("/nobleMetalr")
    public String stockr(){
        return "nobleMetalAlso";
    }

    @RequestMapping(value="needPricer")
    public void pushRight(HttpServletResponse response){
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("utf-8");
        Random r = new Random();
        try {
            PrintWriter pw = response.getWriter();
            int i = 0;
            while(i<10){
                if(pw.checkError()){
                    System.out.println("客户端断开连接");
                    return;
                }
                Thread.sleep(1000);
                pw.write(makeResp(r));
                pw.flush();
                i++;
            }
            System.out.println("达到阈值，结束发送.......");
            pw.write("data:stop\n\n");
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
