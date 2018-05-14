package playground.interview;

import org.awaitility.Duration;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.awaitility.Awaitility.await;

class SleepWaitYieldTest {

    /**
     * Thread.sleep()不会释放当前线程持有的锁
     * @throws InterruptedException
     */
    @Test
    void sleep_not_release_lock() throws InterruptedException {
        Object lock = new Object();
        Thread threadA = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadA.start();

        CountDownLatch latch = new CountDownLatch(1);
        Thread threadB = new Thread(() -> {
            synchronized (lock) {
                latch.countDown();
            }
        });
        threadB.start();

        await().atLeast(Duration.FIVE_HUNDRED_MILLISECONDS)
                .atMost(Duration.FIVE_SECONDS)
                .until(() -> latch.getCount() == 0);
    }
}