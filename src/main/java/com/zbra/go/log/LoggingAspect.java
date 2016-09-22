package com.zbra.go.log;

import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Autowired
    private LogFactory logFactory;

    @Pointcut("within(com.zbra.go..*)")
    public Object invoke(MethodInvocation mi) throws Throwable {
        final Log log = logFactory.createLog(mi.getThis().getClass());
        log.info("Calling method [%s]...", mi.getMethod().getName());
        final Object result = mi.proceed();
        log.info("Returned from method [%s] with result [%s]", mi.getMethod().getName(), result == null ? "none" : result);
        return result;
    }
}
