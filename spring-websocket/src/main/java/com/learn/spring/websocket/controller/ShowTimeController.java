package com.learn.spring.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * autor:liman
 * createtime:2020/11/7
 * comment:
 */
@Controller
public class ShowTimeController {
    @RequestMapping("/time")
    public String normal(){
        return "showtime";
    }

    @RequestMapping(value="/showTime",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }
}
