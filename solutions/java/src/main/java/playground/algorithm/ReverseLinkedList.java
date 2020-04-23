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
}
