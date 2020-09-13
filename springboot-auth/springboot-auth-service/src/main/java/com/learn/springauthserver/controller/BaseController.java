package com.learn.springauthserver.controller;

import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * autor:liman
 * createtime:2019/11/20
 * comment:
 */
@RestController
@Slf4j
@RequestMapping("base")
public class BaseController {

    @RequestMapping("info")
    public BaseResponse info(){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try{
            log.info("请求进入");
            String str = "test";
            String str2 = "test2";

            List<String> messages= new ArrayList<String>();
            messages.add(str);
            messages.add(str2);
            baseResponse.setData(messages);
        }catch (Exception e){
            baseResponse = new BaseResponse(StatusCode.Fail);
            log.error("异常:{}",e.fillInStackTrace());
        }
        return baseResponse;
    }

}
