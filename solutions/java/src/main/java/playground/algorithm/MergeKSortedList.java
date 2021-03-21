package playground.algorithm;

import playground.algorithm.common.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeKSortedList {

    public ListNode mergeKLists(ListNode[] lists) {
        ListNode sentinel = new ListNode(-1);
        ListNode next = sentinel;
        PriorityQueue<ListNode> heap = new PriorityQueue<>(Comparator.comparingInt(l -> l.val));
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                heap.offer(lists[i]);
            }
        }
        while (!heap.isEmpty()) {
            ListNode peek = heap.poll();
            next = next.next = peek;
            if (peek.next != null) {
                heap.offer(peek.next);
            }
        }
        next.next = null;
        return sentinel.next;
    }
}
