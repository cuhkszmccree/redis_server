package com.example.redis_service.log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
@Slf4j
public class LogAspect {
    @Before("execution(* com.example.redis_service.Service.Impl.RtDataImpl.*(..))")
    public void before(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("Redis operation Prepared");
        System.out.println("Log Aspect " + method.getName());
    }
    @After("execution(* com.example.redis_service.Service.Impl.RtDataImpl.*(..))")
    public void after(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("Redis operation Success");
        System.out.println("Log Aspect " + method.getName());
    }
}