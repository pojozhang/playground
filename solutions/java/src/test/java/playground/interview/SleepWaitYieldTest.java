package playground.interview;

import org.awaitility.Duration;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

class SleepWaitYieldTest {

    /**
     * sleep()，wait()，yield()都会让出CPU时间。
     * sleep()是一个静态方法，它不会释放当前线程持有的锁。
     * sleep()中指定的时间是线程不会运行的最短时间，不能保证该线程睡眠到期后就开始执行。
     *
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

    /**
     * sleep()会把线程置为TIMED_WAITING状态。
     *
     * @throws InterruptedException
     */
    @Test
    void sleep_sets_the_state_of_current_thread_to_timed_waiting() throws InterruptedException {
        Object lock = new Object();
        Thread thread = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        await().atMost(Duration.FIVE_SECONDS)
                .until(() -> thread.getState() == Thread.State.TIMED_WAITING);
    }

    /**
     * yield()方法把当前线程置为可运行（RUNNABLE）状态，以允许具有相同优先级的其他线程获得运行机会。
     * 让出CPU时间后可能会被线程调度程序再次选中并执行，因此不能保证达到让步目的。
     */
    @Test
    void yield() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        int RETRY_ATTEMPTS = 10;

        for (int n = 0; n < RETRY_ATTEMPTS; n++) {

            Thread threadA = new Thread(() -> {
                for (int i = 0; i < 100; i++) {
                    if (i == 10) {
                        Thread.yield();
                    }
                }
            });
            threadA.start();

            Thread threadB = new Thread(() -> {
                for (int i = 0; i < 100; i++) {
                    if (threadA.getState() == Thread.State.RUNNABLE) {
                        latch.countDown();
                    }
                }
            });
            threadB.start();

            if (latch.await(1, TimeUnit.SECONDS)) {
                break;
            }
        }
    }
}