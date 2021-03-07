package com.learn.seckill.server.controller;

import com.learn.enums.StatusCode;
import com.learn.response.BaseResponse;
import com.learn.seckill.model.dto.KillSuccessUserInfo;
import com.learn.seckill.server.dto.KillDto;
import com.learn.seckill.server.service.IRobService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value="/execute",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse robOrder(@RequestBody @Validated KillDto killDto, BindingResult bindingResult,HttpSession httpSession){
        if (bindingResult.hasErrors() || killDto.getKillId()<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }

        Integer userId=killDto.getUserId();
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            Boolean res=robService.killItem(killDto.getKillId(),userId);
            if (!res){
                return new BaseResponse(StatusCode.Fail.getCode(),"哈哈~商品已抢购完毕或者不在抢购时间段哦!");
            }
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        log.info("抢购商品返回的结果为:{}",response);
        return response;
    }

    /**
     * mysql层面的简单优化
     * @param killDto
     * @param bindingResult
     * @param httpSession
     * @return
     */
    @RequestMapping(value="/execute/mysqlUpdate",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse robOrderMySQLUpdate(@RequestBody @Validated KillDto killDto, BindingResult bindingResult,HttpSession httpSession){
        if (bindingResult.hasErrors() || killDto.getKillId()<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }

        Integer userId=killDto.getUserId();
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            Boolean res=robService.killItemMySQLUpdate(killDto.getKillId(),userId);
            if (!res){
                return new BaseResponse(StatusCode.Fail.getCode(),"哈哈~商品已抢购完毕或者不在抢购时间段哦!");
            }
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        log.info("抢购商品返回的结果为:{}",response);
        return response;
    }

    /**
     * 基于Redis分布式锁的抢单实例
     * @param killDto
     * @param bindingResult
     * @param httpSession
     * @return
     */
    @RequestMapping(value="/execute/redisLockUpdate",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse robOrderRedisLockUpdate(@RequestBody @Validated KillDto killDto, BindingResult bindingResult,HttpSession httpSession){
        if (bindingResult.hasErrors() || killDto.getKillId()<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }

        Integer userId=killDto.getUserId();
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            Boolean res=robService.killItemRedisLockUpdate(killDto.getKillId(),userId);
            if (!res){
                return new BaseResponse(StatusCode.Fail.getCode(),"哈哈~商品已抢购完毕或者不在抢购时间段哦!");
            }
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        log.info("抢购商品返回的结果为:{}",response);
        return response;
    }

    /**
     * 基于 Redisson 分布式锁的抢单实例
     * @param killDto
     * @param bindingResult
     * @param httpSession
     * @return
     */
    @RequestMapping(value="/execute/redissonLockUpdate",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse robOrderRedissonLockUpdate(@RequestBody @Validated KillDto killDto, BindingResult bindingResult,HttpSession httpSession){
        if (bindingResult.hasErrors() || killDto.getKillId()<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }

        Integer userId=killDto.getUserId();
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            Boolean res=robService.killItemRedissonLockUpdate(killDto.getKillId(),userId);
            if (!res){
                return new BaseResponse(StatusCode.Fail.getCode(),"哈哈~商品已抢购完毕或者不在抢购时间段哦!");
            }
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        log.info("抢购商品返回的结果为:{}",response);
        return response;
    }

    /**
     * 基于 zookeeper 分布式锁的抢单实例
     * @param killDto
     * @param bindingResult
     * @param httpSession
     * @return
     */
    @RequestMapping(value="/execute/zookeperLockUpdate",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BaseResponse robOrderZookeeperLockUpdate(@RequestBody @Validated KillDto killDto, BindingResult bindingResult,HttpSession httpSession){
        if (bindingResult.hasErrors() || killDto.getKillId()<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }

        Integer userId=killDto.getUserId();
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            Boolean res=robService.killItemZookeeperLockUpdate(killDto.getKillId(),userId);
            if (!res){
                return new BaseResponse(StatusCode.Fail.getCode(),"哈哈~商品已抢购完毕或者不在抢购时间段哦!");
            }
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        log.info("抢购商品返回的结果为:{}",response);
        return response;
    }

    @RequestMapping(value="/record/detail/{orderNo}",method=RequestMethod.GET)
    public String robProductDetail(@PathVariable String orderNo,ModelMap modelMap){
        if(StringUtils.isBlank(orderNo)){
            return "error";
        }
        KillSuccessUserInfo robProductDetail = robService.getRobProductDetail(orderNo);
        if(null == robProductDetail){
            return "error";
        }
        modelMap.put("info",robProductDetail);
        return "killRecord";
    }

}
