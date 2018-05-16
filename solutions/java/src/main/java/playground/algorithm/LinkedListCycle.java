package playground.algorithm;

public class LinkedListCycle {

    static class ListNode {

        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

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
