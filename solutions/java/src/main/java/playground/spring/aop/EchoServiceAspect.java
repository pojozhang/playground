package playground.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EchoServiceAspect {

    @Pointcut("execution(* playground.spring.service.EchoService.echo(String))")
    private void echoPointcut() {
    }

    @Before("echoPointcut()")
    void before(JoinPoint joinPoint) {
    }

    @After(value = "echoPointcut()")
    void after(JoinPoint joinPoint) {
    }

    @AfterReturning(value = "echoPointcut()")
    void afterRunning(JoinPoint joinPoint) {
    }

    @AfterThrowing(value = "echoPointcut()")
    void afterThrowing(JoinPoint joinPoint) {
    }

    @Around(value = "echoPointcut()")
    void around(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();
    }

}
