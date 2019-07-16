package playground.algorithm;

import playground.algorithm.common.ListNode;

public class LinkedListCycle {

    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            if (fast == slow) {
                return true;
            }
            slow = slow.next;
        }
        return false;
    }
}
