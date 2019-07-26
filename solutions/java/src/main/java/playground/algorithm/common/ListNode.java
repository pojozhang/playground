package playground.algorithm.common;

import java.util.StringJoiner;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }

    public static ListNode of(int... values) {
        ListNode head = new ListNode(values[0]);
        ListNode tail = head;
        for (int i = 1; i < values.length; i++) {
            tail = tail.next = new ListNode(values[i]);
        }
        return head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListNode listNode = (ListNode) o;
        ListNode nextNode = this;
        while (nextNode != null && listNode != null) {
            if (nextNode.val != listNode.val) {
                return false;
            }
            nextNode = nextNode.next;
            listNode = listNode.next;
        }
        return nextNode == null && listNode == null;
    }

    @Override
    public String toString() {
        final int maxDepth = 20;
        StringJoiner joiner = new StringJoiner("->");
        int depth = 0;
        for (ListNode node = this; node != null && depth++ < 100; node = node.next) {
            joiner.add(String.valueOf(node.val));
        }
        return joiner.toString();
    }
}
