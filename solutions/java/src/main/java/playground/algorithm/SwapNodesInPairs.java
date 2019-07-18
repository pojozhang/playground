package playground.algorithm;

import playground.algorithm.common.ListNode;

public class SwapNodesInPairs {

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 为了方便代码的处理，这里使用了哨兵。
        ListNode sentinel = new ListNode(-1);
        sentinel.next = head;
        swapPairsRecursive(sentinel);
        return sentinel.next;
    }

    /*
    fixed是指固定不动的节点，比如链表1->2->3->4中，1和2交换后是2->1->3->4，下一次交换后链表变成
    2->1->4->3，在第二次交换中的1节点是固定不动的。
     */
    private void swapPairsRecursive(ListNode fixed) {
        ListNode node1, node2;
        if ((node1 = fixed.next) == null || (node2 = fixed.next.next) == null) {
            return;
        }

        ListNode temp = node2.next;
        node2.next = node1;
        node1.next = temp;
        fixed.next = node2;
        // 两两交换后的后面一个节点作为固定节点，向后推进。
        swapPairsRecursive(node1);
    }
}
