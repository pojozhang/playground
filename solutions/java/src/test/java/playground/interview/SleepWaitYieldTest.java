package playground.interview;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

class SleepWaitYieldTest {

    /*
     * sleep()，wait()，yield()都会让出CPU时间。
     * sleep()是一个静态方法，它不会释放当前线程持有的锁。
     * sleep()中指定的时间是线程不会运行的最短时间，不能保证该线程睡眠到期后就开始执行。
     */
    @Test
    void sleep_not_release_lock() {
        Object lock = new Object();
        Thread threadA = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadA.start();

        CountDownLatch latch = new CountDownLatch(1);
        Thread threadB = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                latch.countDown();
            }
        });
        threadB.start();

        await().atLeast(Duration.ofSeconds(1))
                .atMost(Duration.ofSeconds(10))
                .until(() -> latch.getCount() == 0);
    }

    /*
     * sleep()会把线程置为TIMED_WAITING状态。
     */
    @Test
    void sleep_sets_the_state_of_current_thread_to_timed_waiting() {
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

        await().atMost(Duration.ofSeconds(5))
                .until(() -> thread.getState() == Thread.State.TIMED_WAITING);
    }

    /*
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

    /*
     * wait()方法会释放对象上的锁。
     * wait()、notify()、notifyAll()方法都必须写在同步块中，否则运行时抛出java.lang.IllegalMonitorStateException异常。
     */
    @Test
    void wait_notify() throws InterruptedException {
        Object lock = new Object();

        Thread waitThread = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        waitThread.start();

        Thread.sleep(2000);

        Thread notifyThread = new Thread(() -> {
            synchronized (lock) {
                lock.notify();
            }
        });
        notifyThread.start();

        waitThread.join(5000);
        notifyThread.join(5000);
    }
}