package playground.algorithm;

import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("unchecked")
public class ImplementStackUsingQueues {

    public static class MyStack {

        private Queue<Integer>[] queues;
        private Queue<Integer> inQueue;
        private Queue<Integer> outQueue;

        /**
         * Initialize your data structure here.
         */
        public MyStack() {
            queues = new Queue[2];
            queues[0] = new LinkedList<>();
            queues[1] = new LinkedList<>();
            inQueue = queues[0];
            outQueue = queues[1];
        }

        /**
         * Push element x onto stack.
         */
        public void push(int x) {
            inQueue.add(x);
        }

        /**
         * Removes the element on top of the stack and returns that element.
         */
        public int pop() {
            while (inQueue.size() > 1) {
                outQueue.add(inQueue.poll());
            }
            Integer peek = inQueue.poll();
            switchQueues();
            return peek;
        }

        /**
         * Get the top element.
         */
        public int top() {
            while (inQueue.size() > 1) {
                outQueue.add(inQueue.poll());
            }
            Integer peek = inQueue.poll();
            outQueue.add(peek);
            switchQueues();
            return peek;
        }

        /**
         * Returns whether the stack is empty.
         */
        public boolean empty() {
            return inQueue.isEmpty();
        }

        private void switchQueues() {
            inQueue = inQueue == queues[0] ? queues[1] : queues[0];
            outQueue = outQueue == queues[1] ? queues[0] : queues[1];
        }
    }
}
