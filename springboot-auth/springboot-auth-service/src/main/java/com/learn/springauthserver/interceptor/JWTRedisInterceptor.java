package com.learn.springauthserver.interceptor;

import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import com.learn.springauthserver.service.CommonService;
import com.learn.springauthserver.service.JWTRedisAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * autor:liman
 * createtime:2019/12/19
 * comment:
 */
//@Component
@Slf4j
public class JWTRedisInterceptor implements HandlerInterceptor {

    @Autowired
    private CommonService commonService;

    @Autowired
    private JWTRedisAuthService jwtRedisAuthService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            String accessToken = request.getHeader("accessToken");
            if(StringUtils.isBlank(accessToken)){
                log.error("[JWT+Redis认证]token验证失败，token不能为空");
                BaseResponse result = new BaseResponse(StatusCode.Fail.getCode(),"accessToken不能为空");
                commonService.print(response,result);
            }else{
                log.info("[JWT+Redis认证]用户开始认证");
                BaseResponse result = jwtRedisAuthService.validateToken(accessToken);
                if(Objects.equals(result.getStatus(),StatusCode.Success.getCode())){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        if (response.getStatus()==500){
            modelAndView.setViewName("/error/500");
        }else if (response.getStatus()==404){
            modelAndView.setViewName("/error/404");
        }
    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，
     * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}
