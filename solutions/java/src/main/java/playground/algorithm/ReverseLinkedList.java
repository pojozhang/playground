package playground.algorithm;

import playground.algorithm.common.ListNode;

public class ReverseLinkedList {

    public ListNode reverseList(ListNode head) {
        ListNode last = null;
        ListNode current = head;
        ListNode next;
        while (current != null) {
            next = current.next;
            current.next = last;
            last = current;
            current = next;
        }
        return last;
    }

    public ListNode reverseListRecursively(ListNode head) {
        return reverseListRecursively(null, head);
    }

    private ListNode reverseListRecursively(ListNode current, ListNode next) {
        if (next == null) {
            return current;
        }
        ListNode head = reverseListRecursively(next, next.next);
        next.next = current;
        return head;
    }
}
