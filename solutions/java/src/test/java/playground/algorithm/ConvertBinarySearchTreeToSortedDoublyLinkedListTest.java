package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ConvertBinarySearchTreeToSortedDoublyLinkedListTest {

    private ConvertBinarySearchTreeToSortedDoublyLinkedList solution = new ConvertBinarySearchTreeToSortedDoublyLinkedList();

    @Test
    void case_1() {
        ConvertBinarySearchTreeToSortedDoublyLinkedList.Node root = new ConvertBinarySearchTreeToSortedDoublyLinkedList.Node(4);
        root.left = new ConvertBinarySearchTreeToSortedDoublyLinkedList.Node(2);
        root.right = new ConvertBinarySearchTreeToSortedDoublyLinkedList.Node(5);
        root.left.left = new ConvertBinarySearchTreeToSortedDoublyLinkedList.Node(1);
        root.left.right = new ConvertBinarySearchTreeToSortedDoublyLinkedList.Node(3);

        ConvertBinarySearchTreeToSortedDoublyLinkedList.Node head = solution.treeToDoublyList(root);

        assertThat(head.val).isEqualTo(1);
        assertThat(head.left.val).isEqualTo(2);
        assertThat(head.right.val).isEqualTo(5);
        assertThat(head.left.left.val).isEqualTo(3);
        assertThat(head.left.right.val).isEqualTo(1);
        assertThat(head.left.left.left.val).isEqualTo(4);
        assertThat(head.left.left.right.val).isEqualTo(2);
        assertThat(head.left.left.left.left.val).isEqualTo(5);
        assertThat(head.left.left.left.right.val).isEqualTo(3);
        assertThat(head.left.left.left.left.left.val).isEqualTo(1);
        assertThat(head.left.left.left.left.right.val).isEqualTo(4);
    }
}