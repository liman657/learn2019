package com.liman.learn.pmp.controller.log;

import com.google.common.collect.Maps;
import com.liman.learn.common.response.BaseResponse;
import com.liman.learn.common.response.StatusCode;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.pmp.server.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * autor:liman
 * createtime:2021/2/13
 * comment:
 */
@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private ILogService logService;

    //列表
    @ResponseBody
    @RequestMapping("/list")
    public BaseResponse list(@RequestParam Map<String, Object> params){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap= Maps.newHashMap();
        try {
            log.info("日志模块-列表查询");
            PageUtil page = logService.queryPage(params);
            resMap.put("page", page);
            response.setData(resMap);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    //清除
    @ResponseBody
    @RequestMapping("/truncate")
    @RequiresPermissions("sys:log:truncate")
    public BaseResponse truncate(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            logService.truncate();
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
