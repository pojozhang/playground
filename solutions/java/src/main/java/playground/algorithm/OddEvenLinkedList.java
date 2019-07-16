package playground.algorithm;

import playground.algorithm.common.ListNode;

public class OddEvenLinkedList {

    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode oddTail = head;
        ListNode evenHead = head.next;
        ListNode evenTail = evenHead;
        ListNode next = head.next.next;
        int count = 0;
        while (next != null) {
            if (count % 2 == 0) {
                oddTail = oddTail.next = next;
            } else {
                evenTail = evenTail.next = next;
            }
            next = next.next;
            count++;
        }
        evenTail.next = null;
        oddTail.next = evenHead;
        return head;
    }
}
