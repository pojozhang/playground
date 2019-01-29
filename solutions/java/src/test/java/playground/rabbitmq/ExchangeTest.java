package playground.rabbitmq;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExchangeTest extends BaseRabbitmqTest {

    @Test
    void a_message_goes_to_the_queues_whose_binding_key_exactly_matches_the_routing_key_when_using_direct_exchange() throws IOException, InterruptedException {
        final String targetQueue = "queue-1";
        final String targetRoutingKey = "routing-key-1";
        final String anotherQueue = "queue-2";
        final String anotherRoutingKey = "routing-key-2";
        final String payload = "payload";
        declareQueueAndBind(DIRECT_EXCHANGE, targetQueue, targetRoutingKey);
        declareQueueAndBind(DIRECT_EXCHANGE, anotherQueue, anotherRoutingKey);

        assertEquals(payload, publishAndConsume(DIRECT_EXCHANGE, targetRoutingKey, payload, targetQueue, 5, TimeUnit.SECONDS));
        assertThrows(TimeoutException.class, () -> publishAndConsume(DIRECT_EXCHANGE, targetRoutingKey, payload, anotherQueue, 5, TimeUnit.SECONDS));
    }

    @Test
    void a_fanout_exchange_broadcasts_all_the_messages_it_receives_to_all_the_queues_it_knows() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        final String firstQueue = "queue-1";
        final String secondQueue = "queue-2";
        final String emptyRoutingKey = "";
        final String payload = "payload";
        declareQueueAndBind(FANOUT_EXCHANGE, firstQueue, emptyRoutingKey);
        declareQueueAndBind(FANOUT_EXCHANGE, secondQueue, emptyRoutingKey);

        assertEquals(payload, publishAndConsume(FANOUT_EXCHANGE, emptyRoutingKey, payload, firstQueue, 5, TimeUnit.SECONDS));
        assertEquals(payload, publishAndConsume(FANOUT_EXCHANGE, emptyRoutingKey, payload, secondQueue, 5, TimeUnit.SECONDS));
    }

    @Test
    void a_message_sent_with_a_particular_routing_key_will_be_delivered_to_all_the_queues_that_are_bound_with_a_matching_binding_key_when_using_topic_exchange() throws IOException, InterruptedException {
        final String firstQueue = "queue-1";
        final String secondQueue = "queue-2";
        final String thirdQueue = "queue-3";
        final String routingKey = "orders.1.status";
        final String payload = "payload";
        declareQueueAndBind(TOPIC_EXCHANGE, firstQueue, "orders.*.status");
        declareQueueAndBind(TOPIC_EXCHANGE, secondQueue, "orders.#");
        declareQueueAndBind(TOPIC_EXCHANGE, thirdQueue, "nothing.match");

        assertEquals(payload, publishAndConsume(TOPIC_EXCHANGE, routingKey, payload, firstQueue, 5, TimeUnit.SECONDS));
        assertEquals(payload, publishAndConsume(TOPIC_EXCHANGE, routingKey, payload, secondQueue, 5, TimeUnit.SECONDS));
        assertThrows(TimeoutException.class, () -> publishAndConsume(TOPIC_EXCHANGE, routingKey, payload, thirdQueue, 5, TimeUnit.SECONDS));
    }
}
