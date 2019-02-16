package playground.rabbitmq;

import com.rabbitmq.client.*;
import com.rabbitmq.http.client.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseRabbitmqTest {

    static final String VIRTUAL_HOST = "playground";
    static final String DIRECT_EXCHANGE = "playground-direct";
    static final String FANOUT_EXCHANGE = "playground-fanout";
    static final String TOPIC_EXCHANGE = "playground-topic";

    private Connection connection;
    private Map<String, BlockingQueue<Delivery>> consumerQueues = new ConcurrentHashMap<>();
    private Set<String> declaredQueues = new HashSet<>();
    Channel channel;
    Client client;

    @BeforeAll
    void init() throws IOException, TimeoutException, URISyntaxException {
        initHttpClient();
        initConnections();
        initExchanges();
    }

    private void initHttpClient() throws MalformedURLException, URISyntaxException {
        client = new Client("http://test:test@localhost:15672/api/");
    }

    private void initConnections() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");
        connectionFactory.setVirtualHost(VIRTUAL_HOST);
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.confirmSelect();
    }

    private void initExchanges() throws IOException {
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);
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
        consumerQueues.clear();
        declaredQueues.clear();
    }

    void declareQueueAndBind(String exchange, String queue, String routingKey) throws IOException {
        declareQueue(queue, false, false, false, null);
        channel.queueBind(queue, exchange, routingKey);
    }

    void startConsume(String... queues) throws IOException {
        for (String queue : queues) {
            if (consumerQueues.containsKey(queue)) {
                continue;
            }
            consumerQueues.put(queue, new LinkedBlockingQueue<>());
            channel.basicConsume(queue, false,
                    (consumerTag, message) -> {
                        BlockingQueue<Delivery> deliveryQueue = consumerQueues.get(queue);
                        if (deliveryQueue != null) {
                            deliveryQueue.add(message);
                        }
                    },
                    consumerTag -> {
                    });
        }
    }

    void declareQueue(String queue, boolean durable, boolean exclusive, boolean autoDelete,
                      Map<String, Object> arguments) throws IOException {
        channel.queueDeclare(queue, durable, exclusive, autoDelete, arguments);
        declaredQueues.add(queue);
    }

    String publishAndReceive(String exchange, String routingKey, String payload, String queue, long timeout, TimeUnit unit) throws IOException, InterruptedException, TimeoutException {
        publish(exchange, routingKey, payload);
        return getMessage(receive(queue, timeout, unit));
    }

    void publish(String exchange, String routingKey, String payload) throws IOException {
        channel.basicPublish(exchange, routingKey, MessageProperties.TEXT_PLAIN, payload.getBytes(StandardCharsets.UTF_8));
    }

    Delivery receive(String queue, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        Delivery delivery = consumerQueues.get(queue).poll(timeout, unit);
        if (delivery != null) {
            return delivery;
        }
        throw new TimeoutException();
    }

    void ack(Delivery delivery) throws IOException {
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    }

    void nack(Delivery delivery, boolean requeue) throws IOException {
        channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, requeue);
    }

    void reject(Delivery delivery, boolean requeue) throws IOException {
        channel.basicReject(delivery.getEnvelope().getDeliveryTag(), requeue);
    }

    boolean waitForConfirms(long timeout, TimeUnit unit) throws TimeoutException, InterruptedException {
        return channel.waitForConfirms(unit.toMillis(timeout));
    }

    String getMessage(Delivery delivery) {
        return new String(delivery.getBody(), StandardCharsets.UTF_8);
    }
}
