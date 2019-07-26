package playground.algorithm;

import playground.algorithm.common.ListNode;

public class ReverseNodesInKGroup {

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k < 2) {
            return head;
        }

        ListNode sentinel = new ListNode(-1);
        sentinel.next = head;
        reverseKGroup(sentinel, sentinel.next, k, size(head));
        return sentinel.next;
    }

    private int size(ListNode node) {
        int size = 0;
        for (; node != null; node = node.next) {
            size++;
        }
        return size;
    }

    // fixed->current(head)->next
    private void reverseKGroup(ListNode fixed, ListNode head, int k, int left) {
        if (left < k) {
            fixed.next = head;
            return;
        }

        ListNode previous = null;
        ListNode current = head;
        ListNode next;
        for (int i = 0; current != null && i < k; i++) {
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        fixed.next = previous;
        reverseKGroup(head, current, k, left - k);
    }
}
