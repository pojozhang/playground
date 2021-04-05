package playground.algorithm;

public class MyCircularQueue {

    private int[] array;
    private int writePos;
    private int readPos;

    public MyCircularQueue(int k) {
        this.array = new int[k];
        this.writePos = 0;
        this.readPos = 0;
    }

    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        this.array[writePos++ % array.length] = value;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        readPos++;
        return true;
    }

    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return array[readPos % array.length];
    }

    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        return array[(writePos - 1) % array.length];
    }

    public boolean isEmpty() {
        return writePos == readPos;
    }

    public boolean isFull() {
        return writePos > readPos && ((writePos - readPos) % array.length == 0);
    }
}
