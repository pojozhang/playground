package playground.rabbitmq;

import com.rabbitmq.client.Delivery;
import org.awaitility.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.awaitility.Awaitility.await;

class ConfirmationTest extends BaseRabbitmqTest {

    private static final String QUEUE = "queue-1";

    @BeforeEach
    void setUp() throws IOException {
        declareQueueAndBind(DIRECT_EXCHANGE, QUEUE, QUEUE);
        publish(DIRECT_EXCHANGE, QUEUE, "payload");
        startConsume(QUEUE);
    }

    @Test
    void ack() throws IOException, InterruptedException, TimeoutException {
        Delivery delivery = receive(QUEUE, 5, TimeUnit.SECONDS);

        await().pollInterval(Duration.ONE_SECOND)
                .atMost(Duration.ONE_MINUTE)
                .until(() -> unacknowledgedMessages(QUEUE) == 1);

        ack(delivery);

        await().pollInterval(Duration.ONE_SECOND)
                .atMost(Duration.ONE_MINUTE)
                .until(() -> unacknowledgedMessages(QUEUE) == 0);
    }

    @Test
    void nack() throws IOException, InterruptedException, TimeoutException {
        Delivery firstDelivery = receive(QUEUE, 5, TimeUnit.SECONDS);

        await().pollInterval(Duration.ONE_SECOND)
                .atMost(Duration.ONE_MINUTE)
                .until(() -> unacknowledgedMessages(QUEUE) == 1);

        nack(firstDelivery, true);
        Delivery secondDelivery = receive(QUEUE, 5, TimeUnit.SECONDS);
        nack(secondDelivery, false);

        await().pollInterval(Duration.ONE_SECOND)
                .atMost(Duration.ONE_MINUTE)
                .until(() -> unacknowledgedMessages(QUEUE) == 0);
    }

    @Test
    void reject() throws IOException, InterruptedException, TimeoutException {
        Delivery firstDelivery = receive(QUEUE, 5, TimeUnit.SECONDS);

        await().pollInterval(Duration.ONE_SECOND)
                .atMost(Duration.ONE_MINUTE)
                .until(() -> unacknowledgedMessages(QUEUE) == 1);

        reject(firstDelivery, true);
        Delivery secondDelivery = receive(QUEUE, 5, TimeUnit.SECONDS);
        reject(secondDelivery, false);

        await().pollInterval(Duration.ONE_SECOND)
                .atMost(Duration.ONE_MINUTE)
                .until(() -> unacknowledgedMessages(QUEUE) == 0);
    }

    private long unacknowledgedMessages(String queue) {
        return client.getQueue(VIRTUAL_HOST, queue).getMessagesUnacknowledged();
    }
}
