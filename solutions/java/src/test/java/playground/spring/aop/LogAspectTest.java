package playground.spring.aop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import playground.spring.SpringTestBase;
import playground.spring.service.EchoService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class LogAspectTest extends SpringTestBase {

    @SpyBean
    private LogAspect logAspect;
    @SpyBean
    private EchoService echoService;

    @Test
    void should_call_around() throws Throwable {
        echoService.echo("string");

        verify(logAspect).around(any(), any());
    }
}