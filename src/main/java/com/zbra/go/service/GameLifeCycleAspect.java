package com.zbra.go.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GameLifeCycleAspect {

    @Autowired
    private GameEngineService gameEngineService;

    @Pointcut("execution(* com.zbra.go.service.ImageService.*(..))")
    public void imageService() {
    }

    @Around("imageService()")
    public Object validate(ProceedingJoinPoint point) throws Throwable {

        // TODO
        // - check if team current level was completed, if was completed set team to next level

        return point.proceed();
    }
}
