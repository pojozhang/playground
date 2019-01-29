package playground.rabbitmq;

import com.rabbitmq.client.Delivery;
import org.awaitility.Duration;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfirmationTest extends BaseRabbitmqTest {

    private static final String QUEUE = "queue-1";

    @Test
    void ack() throws IOException, InterruptedException {
        declareQueueAndBind(DIRECT_EXCHANGE, QUEUE, QUEUE);
        publish(DIRECT_EXCHANGE, QUEUE, "payload");
        assertEquals(0, unacknowledgedMessages(QUEUE));

        Delivery delivery = consume(QUEUE, 5, TimeUnit.SECONDS);

        await().pollInterval(Duration.ONE_SECOND)
                .atMost(Duration.ONE_MINUTE)
                .until(() -> unacknowledgedMessages(QUEUE) == 1);

        ack(delivery);

        await().pollInterval(Duration.ONE_SECOND)
                .atMost(Duration.ONE_MINUTE)
                .until(() -> unacknowledgedMessages(QUEUE) == 0);
    }

    @Test
    void nack() throws IOException, InterruptedException {
        declareQueueAndBind(DIRECT_EXCHANGE, QUEUE, QUEUE);
        publish(DIRECT_EXCHANGE, QUEUE, "payload");
        assertEquals(0, unacknowledgedMessages(QUEUE));

        Delivery firstDelivery = consume(QUEUE, 5, TimeUnit.SECONDS);
//        await().pollInterval(Duration.ONE_SECOND)
//                .atMost(Duration.ONE_MINUTE)
//                .until(() -> unacknowledgedMessages(QUEUE) == 1);

        reject(firstDelivery, true);
//        channel.basicRecover();
//        await().pollInterval(Duration.ONE_SECOND)
//                .atMost(Duration.ONE_MINUTE)
//                .until(() -> unacknowledgedMessages(QUEUE) == 1);

        Thread.sleep(1000000);
//        Delivery secondDelivery = consume(QUEUE, false, 5, TimeUnit.SECONDS);
//        nack(secondDelivery, false);
//        await().pollInterval(Duration.ONE_SECOND)
//                .atMost(Duration.ONE_MINUTE)
//                .until(() -> unacknowledgedMessages(QUEUE) == 0);
    }

    private long unacknowledgedMessages(String queue) {
        return client.getQueue(VIRTUAL_HOST, queue).getMessagesUnacknowledged();
    }
}
