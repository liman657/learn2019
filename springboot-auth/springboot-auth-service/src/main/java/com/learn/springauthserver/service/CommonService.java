package com.learn.springauthserver.service;/**
 * Created by Administrator on 2019/8/30.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.springauthmodel.entity.Log;
import com.learn.springauthmodel.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author:debug (SteadyJack)
 * @Date: 2019/9/9 9:56
 **/
@Service
@EnableAsync
public class CommonService {

    @Autowired
    private ObjectMapper objectMapper;

    public void print(HttpServletResponse response, Object message){
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(message));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}




















