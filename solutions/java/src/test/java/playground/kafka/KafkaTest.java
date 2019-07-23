package playground.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.awaitility.Awaitility.await;

class KafkaTest {

    private static final String BROKERS = "127.0.0.1:29092";
    private static final String STRING_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
    private static final String STRING_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
    private static final String TOPIC = "test-topic-" + UUID.randomUUID().toString().substring(0, 6);
    private static final String GROUP = "test-group";

    @Test
    void producer_should_send_messages_to_the_consumer() throws InterruptedException, TimeoutException, ExecutionException {
        final int MESSAGE_COUNT = 10;
        AtomicInteger countDown = new AtomicInteger(MESSAGE_COUNT);
        Executors.newSingleThreadExecutor().submit(() -> {
            try (Consumer<String, String> consumer = initConsumer()) {
                consumer.subscribe(Collections.singletonList(TOPIC));
                while (countDown.get() > 0) {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                    System.out.println("consume " + records.count());
                    if (!records.isEmpty()) {
                        countDown.addAndGet(-1 * records.count());
                    }
                }
            }
        });

        try (Producer<String, String> producer = initProducer()) {
            for (int i = 0; i < MESSAGE_COUNT; i++) {
                System.out.println("send " + i);
                producer.send(new ProducerRecord<>(TOPIC, "value")).get(5, TimeUnit.SECONDS);
            }
        }

        await().atMost(100, TimeUnit.SECONDS).until(() -> countDown.get() <= 0);
    }

    private Producer<String, String> initProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKERS);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, STRING_SERIALIZER);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, STRING_SERIALIZER);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        return new KafkaProducer<>(properties);
    }

    private Consumer<String, String> initConsumer() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKERS);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, STRING_DESERIALIZER);
        return new KafkaConsumer<>(properties);
    }
}