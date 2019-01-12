package playground.spring.aop;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class EchoServiceAspect {

    @Pointcut("execution(* playground.spring.service.EchoService.echo(..))")
    private void echoPointcut() {
    }

    @Before("echoPointcut() && args(string)")
    void before(String string) {
    }

    @AfterReturning(value = "echoPointcut()", returning = "returnValue")
    void afterRunning(String returnValue) {
    }

    @AfterThrowing(value = "echoPointcut()", throwing = "throwable")
    void afterThrowing(Throwable throwable) {
    }
}
