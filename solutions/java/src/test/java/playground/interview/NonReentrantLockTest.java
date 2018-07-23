package playground.interview;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class NonReentrantLockTest {

    private NonReentrantLock lock = new NonReentrantLock();
    private CountDownLatch latch = new CountDownLatch(2);

    // 不可重入锁
    @Test
    void non_reentrant_lock() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                lock.lock();
                latch.countDown();
                non_reentrant_lock_inner();
                lock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        Thread.sleep(3000);

        assertEquals(1, latch.getCount());
    }

    void non_reentrant_lock_inner() throws InterruptedException {
        lock.lock();
        latch.countDown();
        lock.unlock();
    }
}