package com.liman.learn.pmp.aspect;

import com.google.gson.Gson;
import com.liman.learn.pmp.annotation.LogOperatorAnnotation;
import com.liman.learn.pmp.model.entity.SysLogEntity;
import com.liman.learn.pmp.server.ILogService;
import com.liman.learn.pmp.util.HttpContextUtils;
import com.liman.learn.pmp.util.IPUtil;
import com.liman.learn.pmp.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * autor:liman
 * createtime:2021/2/12
 * comment: 日志操作切面类
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private ILogService logService;

    @Pointcut("@annotation(com.liman.learn.pmp.annotation.LogOperatorAnnotation)")
    public void logOperatorCut(){

    }

    @Around("logOperatorCut()")
    public Object logAroundOperator(ProceedingJoinPoint targetPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = targetPoint.proceed();
        long endTime = System.currentTimeMillis();

        long costTime = endTime - startTime;
        saveOperatorLog(targetPoint,costTime);
        return result;
    }

    /**
     * 记录操作日志到数据库
     * @param targetPoint
     * @param costTime
     */
    public void saveOperatorLog(ProceedingJoinPoint targetPoint,Long costTime){
        //通过point获取方法签名
        MethodSignature signature = (MethodSignature) targetPoint.getSignature();
        Method targetMethod = signature.getMethod();
        SysLogEntity logEntity = new SysLogEntity();

        //获取请求操作的描述信息
        LogOperatorAnnotation logOperatorAnnotation = targetMethod.getAnnotation(LogOperatorAnnotation.class);
        if(null!=logOperatorAnnotation){
            logEntity.setOperation(logOperatorAnnotation.value());
        }

        //获取目标方法名称
        String className = targetPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        logEntity.setMethod(className+"#"+methodName);
        log.info("aop method name:{}",targetMethod.getName());

        //获取请求参数
        Object[] args = targetPoint.getArgs();
        String params = new Gson().toJson(args[0]);
        logEntity.setParams(params);

        //获取IP
        logEntity.setIp(IPUtil.getIpAddr(HttpContextUtils.getHttpServletRequest()));

        //获取其他参数
        logEntity.setCreateDate(DateTime.now().toDate());
        String userName = ShiroUtil.getUserEntity().getUsername();
        logEntity.setUsername(userName);

        //获取执行时间
        logEntity.setTime(costTime);

        logService.save(logEntity);
    }
}
