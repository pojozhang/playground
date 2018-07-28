package playground.interview;

public class MyReentrantLock {

    private int count = 0;
    private boolean isLocked = false;
    private Thread owner;

    public synchronized void lock() throws InterruptedException {
        while (isLocked && Thread.currentThread() != owner) {
            wait();
        }
        isLocked = true;
        count++;
        owner = Thread.currentThread();
    }

    public synchronized void unlock() {
        if (Thread.currentThread() != owner) {
            return;
        }
        count--;
        if (count == 0) {
            isLocked = false;
            owner = null;
            notify();
        }
    }
}
