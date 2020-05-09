package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConvertBinarySearchTreeToSortedDoublyLinkedListTest {

    @Test
    void case_1() {
        ConvertBinarySearchTreeToSortedDoublyLinkedList solution = new ConvertBinarySearchTreeToSortedDoublyLinkedList();
        ConvertBinarySearchTreeToSortedDoublyLinkedList.Node root = new ConvertBinarySearchTreeToSortedDoublyLinkedList.Node(4);
        root.left = new ConvertBinarySearchTreeToSortedDoublyLinkedList.Node(2);
        root.right = new ConvertBinarySearchTreeToSortedDoublyLinkedList.Node(5);
        root.left.left = new ConvertBinarySearchTreeToSortedDoublyLinkedList.Node(1);
        root.left.right = new ConvertBinarySearchTreeToSortedDoublyLinkedList.Node(3);

        ConvertBinarySearchTreeToSortedDoublyLinkedList.Node head = solution.treeToDoublyList(root);

        assertThat(head.val).isEqualTo(1);
        assertThat(head.right.val).isEqualTo(2);
        assertThat(head.left.val).isEqualTo(5);
        assertThat(head.right.right.val).isEqualTo(3);
        assertThat(head.right.left.val).isEqualTo(1);
        assertThat(head.right.right.right.val).isEqualTo(4);
        assertThat(head.right.right.left.val).isEqualTo(2);
        assertThat(head.right.right.right.right.val).isEqualTo(5);
        assertThat(head.right.right.right.left.val).isEqualTo(3);
        assertThat(head.right.right.right.right.right.val).isEqualTo(1);
        assertThat(head.right.right.right.right.left.val).isEqualTo(4);
    }
}