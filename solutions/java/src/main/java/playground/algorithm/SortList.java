package playground.algorithm;

import playground.algorithm.common.ListNode;

public class SortList {

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode fast = head.next, slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode right = sortList(slow.next);
        slow.next = null;
        ListNode left = sortList(head);

        // 合并两个有序链表。
        ListNode sentinel = new ListNode(-1);
        ListNode next = sentinel;
        while (left != null && right != null) {
            if (left.val < right.val) {
                next.next = left;
                left = left.next;
            } else {
                next.next = right;
                right = right.next;
            }
            next = next.next;
        }
        if (left != null) {
            next.next = left;
        } else {
            next.next = right;
        }
        return sentinel.next;
    }
}

