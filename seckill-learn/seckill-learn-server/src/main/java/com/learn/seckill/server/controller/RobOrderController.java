package com.learn.seckill.server.controller;

import com.learn.enums.StatusCode;
import com.learn.response.BaseResponse;
import com.learn.seckill.server.dto.KillDto;
import com.learn.seckill.server.service.IRobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * autor:liman
 * createtime:2021/2/23
 * comment:
 */
@Controller
@RequestMapping("rob")
@Slf4j
public class RobOrderController {

    @Autowired
    private IRobService robService;

    @RequestMapping(value="/rob",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse robOrder(@RequestBody @Validated KillDto killDto, BindingResult bindingResult,HttpSession httpSession){
        if (bindingResult.hasErrors() || killDto.getKillId()<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        Object uId=httpSession.getAttribute("uid");
        if (uId==null){
            return new BaseResponse(StatusCode.UserNotLogin);
        }
        //Integer userId=dto.getUserId();
        Integer userId= (Integer)uId ;

        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            //Boolean res=killService.killItem(dto.getKillId(),userId);
            Boolean res=robService.killItem(killDto.getKillId(),userId);
            if (!res){
                return new BaseResponse(StatusCode.Fail.getCode(),"哈哈~商品已抢购完毕或者不在抢购时间段哦!");
            }
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
