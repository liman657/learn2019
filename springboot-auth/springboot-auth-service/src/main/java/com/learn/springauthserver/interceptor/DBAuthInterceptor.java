package com.learn.springauthserver.interceptor;

import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import com.learn.springauthserver.service.CommonService;
import com.learn.springauthserver.service.DBAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * autor:liman
 * createtime:2019/12/13
 * comment:
 */
//@Component
@Slf4j
public class DBAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private CommonService commonService;

    @Autowired
    private DBAuthService dbAuthService;

    /**
     * 正式进入请求方法之前的拦截操作
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            String accessToken = request.getHeader("accessToken");
            if(StringUtils.isBlank(accessToken)){
                log.error("--db+token认证缺失token参数");
                BaseResponse baseResponse = new BaseResponse(StatusCode.AccessTokenNotExist,"accessToken不存在");
                commonService.print(response,baseResponse);
            }else{
                log.info("-db+token认证开始");
                //这里开始解析并验证token
                BaseResponse result = dbAuthService.validateToken(accessToken);
                if (Objects.equals(result.getStatus(),StatusCode.Success.getCode())){
                    return true;
                }else{
                    commonService.print(response,result);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     *  执行完目标方法之后的操作
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(response.getStatus() == 500){
            modelAndView.setViewName("/error/500");
        }else if(response.getStatus() == 404){
            modelAndView.setViewName("/error/404");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
