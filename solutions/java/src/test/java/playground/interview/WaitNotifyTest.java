package playground.interview;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class WaitNotifyTest {

    @Test
    void wait_notify() throws InterruptedException {
        Thread waitThread = new Thread(() -> {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        waitThread.start();

        Thread.sleep(2000);

        Thread notifyThread = new Thread(() -> {
            synchronized (this) {
                this.notify();
            }
        });

        notifyThread.start();

        waitThread.join();
    }

    @Test
    void wait_notify_all() throws InterruptedException {
        final int WAIT_THREAD = 10;
        CountDownLatch countDownLatch = new CountDownLatch(WAIT_THREAD);
        for (int i = 0; i < WAIT_THREAD; i++) {
            Thread waitThread = new Thread(() -> {
                synchronized (this) {
                    try {
                        this.wait();
                        countDownLatch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            waitThread.start();
        }

        Thread.sleep(2000);

        Thread notifyThread = new Thread(() -> {
            synchronized (this) {
                this.notifyAll();
            }
        });

        notifyThread.start();

        countDownLatch.await(30, TimeUnit.SECONDS);
    }
}
