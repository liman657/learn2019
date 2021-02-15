package com.liman.learn.pmp.exception;

import com.liman.learn.common.response.BaseResponse;
import com.liman.learn.common.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * autor:liman
 * createtime:2021/2/4
 * comment:通用异常处理器
 */
@RestControllerAdvice //用于处理controller的切面，最终结果返回为json格式
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    public BaseResponse handlerAuthorizationException(AuthorizationException e){
        log.warn("用户无权操作该资源");
        BaseResponse hasNotAuth = new BaseResponse(StatusCode.CurrUserHasNotPermission);
        return hasNotAuth;

    }

}
