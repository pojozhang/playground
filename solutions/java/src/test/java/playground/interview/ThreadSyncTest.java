package playground.interview;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class ThreadSyncTest {

    /**
     * CountDownLatch用于等待其它所有线程执行完毕。
     * CountDownLatch只能用一次，无法重置。
     *
     * @throws InterruptedException
     */
    @Test
    void countdownLatch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        Thread threadA = new Thread(latch::countDown);
        threadA.start();
        Thread threadB = new Thread(latch::countDown);
        threadB.start();

        latch.await();
    }

    /**
     * CyclicBarrier用于让一组线程都达到某个状态后再继续同时执行。
     * 与CountDownLatch不同，CyclicBarrier可以重置并再次使用。
     *
     * @throws BrokenBarrierException
     * @throws InterruptedException
     */
    @Test
    void cyclicBarrier() throws BrokenBarrierException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(3);

        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(() -> {
                try {
                    barrier.await();//等待其它线程执行完成
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        barrier.await();//等待其它线程执行完成
    }

    /**
     * CyclicBarrier可以重用。
     *
     * @throws BrokenBarrierException
     * @throws InterruptedException
     */
    @Test
    void cyclicBarrier_reuse() throws BrokenBarrierException, InterruptedException {
        CyclicBarrier barrier = new CyclicBarrier(3);

        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(() -> {
                try {
                    barrier.await();//等待其它线程执行完成
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        barrier.await();//等待其它线程执行完成

        //再次执行上面的代码，可以看到CyclicBarrier被重置了
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        barrier.await();
    }

    /**
     * Semaphore用来控制同时执行的线程的数量。
     */
    @Test
    void semaphore() throws InterruptedException {
        int THREAD_COUNT = 5;
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        //最多两个线程同时执行
        Semaphore semaphore = new Semaphore(2);

        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(() -> {
                try {
                    //获取许可，如果没有则阻塞
                    semaphore.acquire();

                    //业务代码
                    latch.countDown();

                    //释放许可
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }

        latch.await();
    }
}
