package playground.algorithm;

/**
 * 
 * 给定两个非空链表来代表两个非负整数，位数按照逆序方式存储，它们的每个节点只存储单个数字。将这两数相加会返回一个新的链表。
 * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
    示例：
    输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
    输出：7 -> 0 -> 8
    原因：342 + 465 = 807
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
        int t = mun + (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val);
        if (l1 != null || l2 != null) {
            root.next = new ListNode(t % 10);
            add(l1 == null ? null : l1.next, l2 == null ? null : l2.next, root.next, t / 10);
        } else {
            if (t >= 10) {
                root.next = new ListNode(t % 10);
                root.next.next = new ListNode(t / 10);
            } else if (t < 10 && t > 0) {
                root.next = new ListNode(t % 10);
            }
        }
    }

    public long buildNum(ListNode listNode) {
        int index = 10;
        int num = listNode.val;
        while (listNode.next != null) {
            num += listNode.next.val * index;
            index *= 10;
            listNode = listNode.next;
        }
        return num;
    }

    public ListNode buildNode(int[] num) {
        if (num.length == 1) {
            return new ListNode(num[0]);
        }

        ListNode root = new ListNode(num[0]);
        build(root, 1, num);
        return root;
    }

    public void build(ListNode root, int i, int[] num) {
        if (i < num.length) {
            ListNode tmp = new ListNode(num[i]);
            i++;
            root.next = tmp;
            build(tmp, i, num);
        }
    }

    public ListNode buildNode(long num) {
        if (num < 10) {
            return new ListNode((int) num);
        }

        ListNode root = new ListNode((int) num % 10);
        num /= 10;

        build(root, num);
        return root;
    }

    private void build(ListNode root, long num) {
        if (num / 10 > 0) {
            ListNode tmp = new ListNode((int) num % 10);
            root.next = tmp;
            num /= 10;
            build(tmp, num);
        } else {
            root.next = new ListNode((int) num);
        }
    }

    public class ListNode {
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
