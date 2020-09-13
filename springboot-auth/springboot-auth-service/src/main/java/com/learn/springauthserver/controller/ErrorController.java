package com.learn.springauthserver.controller;

import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * autor:liman
 * createtime:2019/12/24
 * comment:如果返回错误，会跳转到这个请求controller
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping(value="500",method = RequestMethod.GET)
    public String error500(){
        return "500";
    }

    @RequestMapping(value="404",method = RequestMethod.GET)
    public String error404(){
        return "404";
    }

    @RequestMapping("unauth")
    @ResponseBody
    public BaseResponse unauth(){
        return new BaseResponse(StatusCode.AccessSessionNotExist);
    }

}
