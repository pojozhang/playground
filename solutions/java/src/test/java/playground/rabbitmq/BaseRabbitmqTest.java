package playground.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseRabbitmqTest {

    static final String DIRECT_EXCHANGE = "playground-direct";
    static final String FANOUT_EXCHANGE = "playground-fanout";
    static final String TOPIC_EXCHANGE = "playground-topic";

    private Connection connection;
    Channel channel;
    Set<String> declaredQueues = new HashSet<>();

    @BeforeAll
    void init() throws IOException, TimeoutException {
        initConnections();
        initExchanges();
    }

    private void initConnections() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");
        connectionFactory.setVirtualHost("playground");
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
    }

    private void initExchanges() throws IOException {
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);
    }

    @AfterAll
    void cleanUp() throws IOException, TimeoutException {
        if (channel != null) {
            channel.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    @AfterEach
    void tearDown() {
        destroyQueues();
    }

    private void destroyQueues() {
        declaredQueues.forEach(queue -> {
            try {
                channel.queueDelete(queue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    void declareQueueAndBind(String exchange, String queue, String routingKey) throws IOException {
        declareQueue(queue, false, false, false, null);
        channel.queueBind(queue, exchange, routingKey);
    }

    void declareQueue(String queue, boolean durable, boolean exclusive, boolean autoDelete,
                      Map<String, Object> arguments) throws IOException {
        channel.queueDeclare(queue, durable, exclusive, autoDelete, arguments);
        declaredQueues.add(queue);
    }

    String publishAndConsume(String exchange, String routingKey, String payload, String queue, long timeout, TimeUnit unit) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        channel.basicPublish(exchange, routingKey, MessageProperties.TEXT_PLAIN, payload.getBytes(StandardCharsets.UTF_8));

        CompletableFuture<String> future = new CompletableFuture<>();
        channel.basicConsume(queue, true,
                (consumerTag, message) -> future.complete(new String(message.getBody(), StandardCharsets.UTF_8)),
                consumerTag -> {
                });
        return future.get(timeout, unit);
    }
}
