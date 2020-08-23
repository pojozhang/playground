package playground.spring.aop;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.boot.test.mock.mockito.SpyBean;
import playground.spring.SpringTestBase;
import playground.spring.service.EchoService;

import static org.mockito.Mockito.*;

class EchoServiceAspectTest extends SpringTestBase {

    @SpyBean
    private EchoServiceAspect echoServiceAspect;
    @SpyBean
    private EchoService echoService;

    @Test
    void should_call_around_and_before_and_after_and_after_running_sequentially_when_no_exception_thrown() throws Throwable {
        echoService.echo("string");

        InOrder inOrder = inOrder(echoServiceAspect);
        inOrder.verify(echoServiceAspect).around(any());
        inOrder.verify(echoServiceAspect).before(any());
        inOrder.verify(echoServiceAspect).afterRunning(any());
        inOrder.verify(echoServiceAspect).after(any());
        inOrder.verify(echoServiceAspect, never()).afterThrowing(any());
    }

    @Test
    void should_call_around_and_before_and_after_throwing_sequentially_when_any_exception_thrown() throws Throwable {
        try {
            echoService.echo(null);
        } catch (NullPointerException ignored) {
        }

        InOrder inOrder = inOrder(echoServiceAspect);
        inOrder.verify(echoServiceAspect).around(any());
        inOrder.verify(echoServiceAspect).before(any());
        inOrder.verify(echoServiceAspect).afterThrowing(any());
        inOrder.verify(echoServiceAspect).after(any());
        inOrder.verify(echoServiceAspect, never()).afterRunning(any());
    }
}