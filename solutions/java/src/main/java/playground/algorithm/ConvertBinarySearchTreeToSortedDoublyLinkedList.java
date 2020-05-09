package playground.algorithm;

public class ConvertBinarySearchTreeToSortedDoublyLinkedList {

    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
        }

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 链表头。
    private Node head;
    // 记录最后一次访问的节点。
    private Node previous;

    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }
        dfs(root);
        head.left = previous;
        previous.right = head;
        return head;
    }

    private void dfs(Node node) {
        if (node == null) {
            return;
        }
        dfs(node.left);
        // 如果previous是空，说明当前节点是二叉搜索树中最左侧的节点。
        if (previous == null) {
            head = node;
        } else {
            previous.right = node;
            node.left = previous;
        }
        previous = node;
        dfs(node.right);
    }
}
