package playground.algorithm;

public class CopyListWithRandomPointer {

    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        // 复制每个节点S，并把复制后的节点S'连接在S后面。
        // S->S'->T->T'
        for (Node next = head; next != null; ) {
            Node tmp = next.next;
            Node copied = new Node(next.val);
            next.next = copied;
            copied.next = tmp;
            next = tmp;
        }

        // 设置拷贝的节点的random指针。
        for (Node next = head; next != null; next = next.next.next) {
            if (next.random == null) {
                continue;
            }
            Node copied = next.next;
            copied.random = next.random.next;
        }

        // 偶数位的节点为原链表节点，奇数位的节点为拷贝后的链表的节点。
        Node copiedHead = head.next;
        Node next = head, copiedNext = copiedHead;
        for (; copiedNext.next != null; next = next.next, copiedNext = copiedNext.next) {
            next.next = next.next.next;
            copiedNext.next = copiedNext.next.next;
        }
        next.next = null;
        return copiedHead;
    }
}
