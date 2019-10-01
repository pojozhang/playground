package playground.algorithm;

import playground.algorithm.common.ListNode;

public class RotateList {

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k < 1) {
            return head;
        }

        int length = 1;
        ListNode slow = head, fast = head;
        while (length <= k && fast.next != null) {
            fast = fast.next;
            length++;
        }
        // 如果k超过了链表中节点的数量，那么进行取模操作。
        if (k >= length) {
            k = k % length;
            // 对k进行取模后如果等于0，那么旋转后的链表等于原链表。
            if (k == 0) {
                return head;
            }
            for (int i = 0; i < length - k - 1; i++) {
                slow = slow.next;
            }
        } else {
            while (fast.next != null) {
                slow = slow.next;
                fast = fast.next;
            }
        }

        fast.next = head;
        head = slow.next;
        slow.next = null;
        return head;
    }
}
