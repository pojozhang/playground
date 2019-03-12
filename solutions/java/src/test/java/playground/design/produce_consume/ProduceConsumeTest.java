package playground.design.produce_consume;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProduceConsumeTest {

    @Test
    void producer_should_be_blocked_if_channel_is_full() {
        int capacity = 5;
        Channel<Object> channel = new Channel<>(capacity);
        Producer<Object> producer = new Producer<>(channel);

        Future<?> future = Executors.newSingleThreadExecutor().submit(() -> {
            for (int i = 0; i <= capacity; i++) {
                try {
                    producer.produce(new Object());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        assertThrows(TimeoutException.class, () -> future.get(5, TimeUnit.SECONDS));
    }

    @Test
    void consumer_should_be_blocked_if_channel_is_empty() {
        Channel<Object> channel = new Channel<>(5);
        Consumer<Object> consumer = new Consumer<>(channel);

        Future<?> future = Executors.newSingleThreadExecutor().submit(() -> {
            try {
                consumer.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        assertThrows(TimeoutException.class, () -> future.get(5, TimeUnit.SECONDS));
    }

    @Test
    void channel_should_be_thread_safe() throws InterruptedException {
        int capacity = 5;
        int count = capacity * 2;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        Channel<Object> channel = new Channel<>(capacity);
        Producer<Object> producer = new Producer<>(channel);
        Consumer<Object> consumer = new Consumer<>(channel);

        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(() -> {
                try {
                    producer.produce(new Object());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(() -> {
                try {
                    consumer.consume();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        countDownLatch.await(10, TimeUnit.SECONDS);
    }
}