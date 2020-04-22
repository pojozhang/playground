package playground.algorithm;

import playground.algorithm.common.ListNode;

public class KthNodeFromEndOfList {

    public int kthToLast(ListNode head, int k) {
        ListNode slow = head, fast = head;
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow.val;
    }
}
