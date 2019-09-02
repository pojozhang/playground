package playground.rabbitmq;

import com.rabbitmq.client.Delivery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfirmationTest extends RabbitmqTestBase {

    private static final String QUEUE = "queue-1";
    private static final String PAYLOAD = "payload";

    @BeforeEach
    void setUp() throws IOException {
        declareQueueAndBind(DIRECT_EXCHANGE, QUEUE, QUEUE);
        startConsume(QUEUE);
    }

    @Test
    void ack() throws IOException, InterruptedException, TimeoutException {
        publish(DIRECT_EXCHANGE, QUEUE, PAYLOAD);
        Delivery delivery = receive(QUEUE, 5, TimeUnit.SECONDS);

        await().pollInterval(Duration.ofSeconds(1))
                .atMost(Duration.ofMinutes(1))
                .until(() -> unacknowledgedMessages(QUEUE) == 1);

        ack(delivery);

        await().pollInterval(Duration.ofSeconds(1))
                .atMost(Duration.ofMinutes(1))
                .until(() -> unacknowledgedMessages(QUEUE) == 0);
    }

    @Test
    void nack() throws IOException, InterruptedException, TimeoutException {
        publish(DIRECT_EXCHANGE, QUEUE, PAYLOAD);
        Delivery firstDelivery = receive(QUEUE, 5, TimeUnit.SECONDS);

        await().pollInterval(Duration.ofSeconds(1))
                .atMost(Duration.ofMinutes(1))
                .until(() -> unacknowledgedMessages(QUEUE) == 1);

        nack(firstDelivery, true);
        Delivery secondDelivery = receive(QUEUE, 5, TimeUnit.SECONDS);
        nack(secondDelivery, false);

        await().pollInterval(Duration.ofSeconds(1))
                .atMost(Duration.ofMinutes(1))
                .until(() -> unacknowledgedMessages(QUEUE) == 0);
    }

    @Test
    void reject() throws IOException, InterruptedException, TimeoutException {
        publish(DIRECT_EXCHANGE, QUEUE, PAYLOAD);
        Delivery firstDelivery = receive(QUEUE, 5, TimeUnit.SECONDS);

        await().pollInterval(Duration.ofSeconds(1))
                .atMost(Duration.ofMinutes(1))
                .until(() -> unacknowledgedMessages(QUEUE) == 1);

        reject(firstDelivery, true);
        Delivery secondDelivery = receive(QUEUE, 5, TimeUnit.SECONDS);
        reject(secondDelivery, false);

        await().pollInterval(Duration.ofSeconds(1))
                .atMost(Duration.ofMinutes(1))
                .until(() -> unacknowledgedMessages(QUEUE) == 0);
    }

    @Test
    void publish_confirm() throws TimeoutException, InterruptedException, IOException {
        publish(DIRECT_EXCHANGE, QUEUE, PAYLOAD);

        assertTrue(waitForConfirms(10, TimeUnit.SECONDS));
    }

    private long unacknowledgedMessages(String queue) {
        return client.getQueue(VIRTUAL_HOST, queue).getMessagesUnacknowledged();
    }
}
