package com.learn.springauthserver.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * autor:liman
 * createtime:2019/12/15
 * comment:统一参数校验工具
 */
public class ValidatorUtils {

    public static String checkResult(BindingResult result){
        StringBuilder stringBuilder = new StringBuilder("");
        if(result != null && result.hasErrors()){
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(error->stringBuilder.append(error.getDefaultMessage()).append("\n"));
        }
        return stringBuilder.toString();
    }

}
