package playground.spring.aop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import playground.spring.BaseSpringTest;
import playground.spring.service.EchoService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class LogAspectTest extends BaseSpringTest {

    @SpyBean
    private LogAspect logAspect;
    @SpyBean
    private EchoService echoService;

    @Test
    void shouldCallBeforeAndAfterRunning() throws Throwable {
        echoService.echo("string");

        verify(logAspect).around(any(), any());
    }
}