package com.liman.learn.pmp.controller;

import com.liman.learn.common.response.BaseResponse;
import com.liman.learn.common.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * autor:liman
 * createtime:2020/12/30
 * comment:
 */
@Controller
@Slf4j
@RequestMapping("test")
public class BaseController {

    @RequestMapping(value="/hello",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse helloWorld(String name){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        if(StringUtils.isBlank(name)){
            name = "helloworld";
        }
        result.setData(name);
        return result;
    }

    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public String getPage(){
        String pageName = "pageOne";
        return pageName;
    }

}
