package com.learn.springboot.redis.service.util;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;

/**
 * autor:liman
 * createtime:2020/7/30
 * comment:
 */
public class ValidatorUtil {
    //TODO:统一校验前端传递过来的参数
    public static String checkResult(BindingResult result){
        StringBuilder sb=new StringBuilder("");
        if (result.hasErrors()){
            /*List<ObjectError> list=result.getAllErrors();
            for (ObjectError error:list){
                sb.append(error.getDefaultMessage()).append("\n");
            }*/

            //java8 steam api
            result.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append("\n"));
        }
        return sb.toString();
    }
}
