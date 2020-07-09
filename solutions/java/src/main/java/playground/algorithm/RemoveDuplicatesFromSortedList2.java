package playground.algorithm;

import playground.algorithm.common.ListNode;

public class RemoveDuplicatesFromSortedList2 {

    public ListNode deleteDuplicates(ListNode head) {
        ListNode sentinel = new ListNode(-1);
        ListNode node = sentinel;
        while (head != null) {
            int value = head.val;
            if (head.next == null || head.next.val != value) {
                node = node.next = new ListNode(value);
                head = head.next;
            } else {
                head = head.next;
                while (head != null && head.val == value) {
                    head = head.next;
                }
            }
        }
        return sentinel.next;
    }
}
