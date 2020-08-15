package com.learn.springboot.redis.service.controller;

import com.google.common.collect.Maps;
import com.learn.springboot.redis.api.response.BaseResponse;
import com.learn.springboot.redis.api.response.StatusCode;
import com.learn.springboot.redis.model.dto.RedPacketDto;
import com.learn.springboot.redis.service.service.redpacket.RedPacketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * autor:liman
 * createtime:2020/8/11
 * comment:模拟抢红包的业务controller
 */
@Slf4j
@RestController
@RequestMapping("red/packet")
public class RedPacketController {

    @Autowired
    private RedPacketService redPacketService;

    /**
     * 发红包
     * @param dto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value="hand/out",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse handOut(@RequestBody RedPacketDto dto, BindingResult bindingResult){
        log.info("发红包请求信息为:{}",dto);
        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap= Maps.newHashMap();
        try{
            resMap.put("redKey",redPacketService.handOut(dto));
            response.setData(resMap);
        }catch (Exception e){
            log.error("发红包出现异常，异常信息为:{}",e);
            response = new BaseResponse(StatusCode.Fail);
        }
        return response;
    }

    /**
     * 抢红包
     * @param userId
     * @param redId
     * @return
     */
    @RequestMapping(value="rob",method=RequestMethod.GET)
    public BaseResponse robRedPacket(@RequestParam Integer userId,@RequestParam String redId){
        log.info("用户:{}，抢红包请求参数:{}",userId,redId);
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
//            Integer getPacketValue = redPacketService.robRedPacket(userId,redId);
//            Integer getPacketValue = redPacketService.robRedPacketVersion02(userId,redId);
            Integer getPacketValue = redPacketService.robRedPackageVersion03(userId,redId);
            if(getPacketValue!=null){
                response.setData(getPacketValue);
            }else{
                return new BaseResponse(StatusCode.Fail);
            }
        }catch (Exception e){
            log.error("抢红包出现异常，异常信息为:{}",e);
            response = new BaseResponse(StatusCode.Fail);
        }
        return response;
    }

}
