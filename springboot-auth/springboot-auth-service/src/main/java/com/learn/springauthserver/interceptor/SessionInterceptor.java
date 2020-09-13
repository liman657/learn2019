package com.learn.springauthserver.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import com.learn.springauthserver.service.SessionAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * autor:liman
 * createtime:2019/12/22
 * comment:基于session认证的拦截模式
 */
//@Component
@Slf4j
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionAuthService sessionAuthService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 进行拦截处理
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        log.info("当前认证的用户sessionId:{}",session);
        //TODO:认证只是判断sessionId是否存在
        if(session.getAttribute(sessionId)!=null){
            return true;
        }
        //TODO:没登录，故而不会有session
        try{
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("Cache-Control","no-cache,must-revalidate");
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(new BaseResponse(StatusCode.AccessSessionNotExist)));
            writer.flush();
            writer.close();
        }catch (Exception e){
            log.error("session 认证异常，异常信息为:{}",e.fillInStackTrace());
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (response.getStatus()==500){
            modelAndView.setViewName("/error/500");
        }else if (response.getStatus()==404){
            modelAndView.setViewName("/error/404");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
