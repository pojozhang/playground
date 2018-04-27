package playground.algorithm;

public class MergeTwoSortedLists {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode mergedRoot = new ListNode(0);
        ListNode it = mergedRoot;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                it.next = new ListNode(l1.val);
                l1 = l1.next;
            } else {
                it.next = new ListNode(l2.val);
                l2 = l2.next;
            }
            it = it.next;
        }

        while (l1 != null) {
            it.next = new ListNode(l1.val);
            l1 = l1.next;
            it = it.next;
        }

        while (l2 != null) {
            it.next = new ListNode(l2.val);
            l2 = l2.next;
            it = it.next;
        }

        return mergedRoot.next;
    }
}
