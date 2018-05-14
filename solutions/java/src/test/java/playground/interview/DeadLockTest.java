package playground.interview;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeadLockTest {

    @Test
    void dead_lock() throws InterruptedException {
        final int COUNT_DOWN = 2;
        CountDownLatch latch = new CountDownLatch(COUNT_DOWN);
        Object lockA = new Object();
        Object lockB = new Object();

        Thread threadA = new Thread(() -> {
            synchronized (lockA) {
                try {
                    Thread.sleep(500);
                    synchronized (lockB) {
                        latch.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadA.start();

        Thread threadB = new Thread(() -> {
            synchronized (lockB) {
                try {
                    Thread.sleep(500);
                    synchronized (lockA) {
                        latch.countDown();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadB.start();

        latch.await(2, TimeUnit.SECONDS);
        assertEquals(COUNT_DOWN, latch.getCount());
    }
}
