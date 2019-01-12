package playground.spring.aop;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.boot.test.mock.mockito.SpyBean;
import playground.spring.BaseSpringTest;
import playground.spring.service.EchoService;

import static org.mockito.Mockito.*;

class EchoServiceAspectTest extends BaseSpringTest {

    @SpyBean
    private EchoServiceAspect echoServiceAspect;
    @SpyBean
    private EchoService echoService;

    @Test
    void shouldCallBeforeAndAfterRunning() {
        final String ECHO_VALUE = "string";

        echoService.echo(ECHO_VALUE);

        InOrder inOrder = inOrder(echoServiceAspect);
        inOrder.verify(echoServiceAspect).before(ECHO_VALUE);
        inOrder.verify(echoServiceAspect).afterRunning(ECHO_VALUE);
        inOrder.verify(echoServiceAspect, never()).afterThrowing(any());
    }

    @Test
    void shouldCallBeforeAndAfterThrowing() {
        final String NULL_VALUE = null;
        final Throwable throwable = new NullPointerException();
        when(echoService.echo(NULL_VALUE)).thenThrow(throwable);

        try {
            echoService.echo(NULL_VALUE);
        } catch (NullPointerException ignored) {
        }

        InOrder inOrder = inOrder(echoServiceAspect);
        inOrder.verify(echoServiceAspect, times(2)).before(NULL_VALUE);
        inOrder.verify(echoServiceAspect).afterThrowing(throwable);
        inOrder.verify(echoServiceAspect, never()).afterRunning(any());
    }
}