package playground.design.produce_consume;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Channel<T> {

    private List<T> list;
    private int capacity;
    private AtomicInteger count;
    private Lock putLock;
    private Lock takeLock;
    private Condition notFull;
    private Condition notEmpty;

    public Channel(int capacity) {
        this.capacity = capacity;
        this.list = Collections.synchronizedList(new LinkedList<>());
        this.count = new AtomicInteger();
        this.putLock = new ReentrantLock();
        this.takeLock = new ReentrantLock();
        this.notFull = this.putLock.newCondition();
        this.notEmpty = this.takeLock.newCondition();
    }

    public void put(T item) throws InterruptedException {
        putLock.lockInterruptibly();
        try {
            while (count.get() == capacity) {
                notFull.await();
            }
            list.add(item);
            count.getAndIncrement();
        } finally {
            putLock.unlock();
        }
        signalNotEmpty();
    }

    private void signalNotEmpty() throws InterruptedException {
        takeLock.lockInterruptibly();
        try {
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }

    public T take() throws InterruptedException {
        T item;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                notEmpty.await();
            }
            item = list.remove(0);
            count.getAndDecrement();
        } finally {
            takeLock.unlock();
        }
        signalNotFull();
        return item;
    }

    private void signalNotFull() throws InterruptedException {
        putLock.lockInterruptibly();
        try {
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }
}
