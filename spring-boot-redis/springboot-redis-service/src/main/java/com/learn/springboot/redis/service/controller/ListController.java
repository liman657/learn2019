package com.learn.springboot.redis.service.controller;

import cn.hutool.core.util.StrUtil;
import com.learn.springboot.redis.api.response.BaseResponse;
import com.learn.springboot.redis.api.response.StatusCode;
import com.learn.springboot.redis.model.entity.Notice;
import com.learn.springboot.redis.model.entity.Product;
import com.learn.springboot.redis.service.service.ListService;
import com.learn.springboot.redis.service.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * autor:liman
 * createtime:2020/7/26
 * comment:redis中的List类型
 */
@RestController
@RequestMapping("list")
@Slf4j
public class ListController {

    @Autowired
    private ListService listService;

    @RequestMapping(value = "put",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse putObjectInList(@RequestBody Product product, BindingResult bindingResult){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            log.info("--商户商品信息:{}--",product);
            response.setData(listService.addProduct(product));
        }catch (Exception e){
            log.error("--List，商户商品-添加-异常:{}--",e);
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @RequestMapping(value="get",method=RequestMethod.GET)
    public BaseResponse getProductByUserId(@RequestParam("userId") final Integer userId){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            response.setData(listService.getHistoryProducts(userId));
        }catch (Exception e){
            log.error("--List，商户商品-列表-异常:{}--",e);
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    //平台发送通知给到各位商户
    @RequestMapping(value = "/notice/put",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse putNotice(@RequestBody @Validated Notice notice, BindingResult result){
        String checkRes=ValidatorUtil.checkResult(result);
        if (StrUtil.isNotBlank(checkRes)){
            return new BaseResponse(StatusCode.Fail.getCode(),checkRes);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("--平台发送通知给到各位商户：{}",notice);

            listService.pushNotice(notice);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
