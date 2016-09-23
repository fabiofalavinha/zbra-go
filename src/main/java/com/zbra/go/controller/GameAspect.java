package com.zbra.go.controller;

import com.zbra.go.service.GameEngineService;
import com.zbra.go.service.GameNotStartedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GameAspect {

    @Autowired
    private GameEngineService gameEngineService;

    @Pointcut("execution(* com.zbra.go.controller.LevelController.*(..))")
    public void levelController() {
    }

    @Around("levelController()")
    public Object validate(ProceedingJoinPoint point) throws Throwable {
        if (!gameEngineService.hasStarted()) {
            throw new GameNotStartedException();
        }
        return point.proceed();
    }
}
