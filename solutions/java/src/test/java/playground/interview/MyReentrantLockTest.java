package playground.interview;

import org.junit.jupiter.api.Test;

class MyReentrantLockTest {

    private MyReentrantLock lock = new MyReentrantLock();

    @Test
    void reentrant_lock() throws InterruptedException {
        lock.lock();
        reentrant_lock_inner();
        lock.unlock();
    }

    private void reentrant_lock_inner() throws InterruptedException {
        lock.lock();
        lock.unlock();
    }
}