package playground.rabbitmq;

import com.rabbitmq.client.MessageProperties;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExchangeTest extends BaseRabbitmqTest {

    @Test
    void a_message_goes_to_the_queues_whose_binding_key_exactly_matches_the_routing_key_when_using_direct_exchange() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        final String TARGET_QUEUE = "queue-1";
        final String TARGET_ROUTING_KEY = "routing-key-1";
        final String ANOTHER_QUEUE = "queue-2";
        final String ANOTHER_ROUTING_KEY = "routing-key-2";
        final String PAYLOAD = "payload";
        declareQueueAndBind(DIRECT_EXCHANGE, TARGET_QUEUE, TARGET_ROUTING_KEY);
        declareQueueAndBind(DIRECT_EXCHANGE, ANOTHER_QUEUE, ANOTHER_ROUTING_KEY);

        assertEquals(PAYLOAD, sendAndReceive(DIRECT_EXCHANGE, TARGET_ROUTING_KEY, PAYLOAD, TARGET_QUEUE, 5, TimeUnit.SECONDS));
        assertThrows(TimeoutException.class, () -> sendAndReceive(DIRECT_EXCHANGE, TARGET_ROUTING_KEY, PAYLOAD, ANOTHER_QUEUE, 5, TimeUnit.SECONDS));
    }

    private void declareQueueAndBind(String exchange, String queue, String routingKey) throws IOException {
        super.declareQueue(queue, false, false, false, null);
        channel.queueBind(queue, exchange, routingKey);
    }

    private String sendAndReceive(String exchange, String routingKey, String payload, String queue, long timeout, TimeUnit unit) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        channel.basicPublish(exchange, routingKey, MessageProperties.TEXT_PLAIN, payload.getBytes(StandardCharsets.UTF_8));

        CompletableFuture<String> future = new CompletableFuture<>();
        channel.basicConsume(queue, false,
                (consumerTag, message) -> future.complete(new String(message.getBody(), StandardCharsets.UTF_8)),
                consumerTag -> {
                });
        return future.get(timeout, unit);
    }
}
