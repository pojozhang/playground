package playground.algorithm;

public class IntersectionOfTwoLinkedLists {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode ptrA = headA, ptrB = headB;
        int lenA = 0, lenB = 0;

        // 计算两个链表的长度
        while (ptrA != null) {
            ptrA = ptrA.next;
            lenA++;
        }
        while (ptrB != null) {
            ptrB = ptrB.next;
            lenB++;
        }

        // longList指向较长的链表，shortList指向较短的链表
        ListNode longList, shortList;
        if (lenA >= lenB) {
            longList = headA;
            shortList = headB;
        } else {
            longList = headB;
            shortList = headA;
        }

        // 较长的链表提前开始遍历
        for (int i = 0; i < Math.abs(lenA - lenB); i++) {
            longList = longList.next;
        }

        // 两个链表同时开始遍历
        while (longList != null) {
            if (longList == shortList) {
                return longList;
            }
            longList = longList.next;
            shortList = shortList.next;
        }

        return null;
    }
}
