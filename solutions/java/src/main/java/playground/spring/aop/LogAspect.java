package playground.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import playground.spring.annotation.Log;

@Aspect
@Component
public class LogAspect {

    @Around(value = "@annotation(log)")
    void around(ProceedingJoinPoint joinPoint, Log log) throws Throwable {
        joinPoint.proceed();
    }
}
