package playground.algorithm;

/**
 * 给定两个非空链表来代表两个非负整数，位数按照逆序方式存储，它们的每个节点只存储单个数字。将这两数相加会返回一个新的链表。
 * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
 * 示例：
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 */
public class AddTwoNumbers {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int n = l1.val + l2.val;
        int mn = n / 10;
        ListNode root = new ListNode(n % 10);
        add(l1.next, l2.next, root, mn);
        return root;
    }

    public void add(ListNode l1, ListNode l2, ListNode root, int mun) {
        int t = mun + getValue(l1) + getValue(l2);
        if (l1 != null || l2 != null) {
            root.next = new ListNode(t % 10);
            add(getNext(l1), getNext(l2), root.next, t / 10);
        } else if (t > 0) {
            root.next = new ListNode(t % 10);
        }
    }

    private int getValue(ListNode listNode) {
        return listNode == null ? 0 : listNode.val;
    }

    private ListNode getNext(ListNode listNode) {
        return listNode == null ? null : listNode.next;
    }

    static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int x) {
            this.val = x;
        }

        @Override
        public String toString() {
            return val + (next == null ? "" : " > " + next.toString());
        }
    }
}
