package playground.algorithm;

import java.util.Deque;
import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class ImplementQueueUsingStacks {

    public static class MyQueue {

        private Deque<Integer>[] stacks;

        /**
         * Initialize your data structure here.
         */
        public MyQueue() {
            stacks = new Deque[2];
            stacks[0] = new LinkedList<>();
            stacks[1] = new LinkedList<>();
        }

        /**
         * Push element x to the back of queue.
         */
        public void push(int x) {
            stacks[0].push(x);
        }

        /**
         * Removes the element from in front of queue and returns that element.
         */
        public int pop() {
            if (stacks[1].isEmpty()) {
                while (!stacks[0].isEmpty()) {
                    stacks[1].push(stacks[0].pop());
                }
            }
            return stacks[1].pop();
        }

        /**
         * Get the front element.
         */
        public int peek() {
            if (stacks[1].isEmpty()) {
                while (!stacks[0].isEmpty()) {
                    stacks[1].push(stacks[0].pop());
                }
            }
            return stacks[1].peek();
        }

        /**
         * Returns whether the queue is empty.
         */
        public boolean empty() {
            return stacks[0].isEmpty() && stacks[1].isEmpty();
        }
    }
}
