package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.*;

class DeleteNodeInALinkedListTest {

    private DeleteNodeInALinkedList solution = new DeleteNodeInALinkedList();

    @Test
    void case_1() {
        ListNode nodeToDelete = new ListNode(5);
        ListNode head = new ListNode(4);
        head.next = nodeToDelete;
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(9);

        solution.deleteNode(nodeToDelete);

        assertEquals(ListNode.of(4, 1, 9), head);
    }

    @Test
    void case_2() {
        ListNode nodeToDelete = new ListNode(1);
        ListNode head = new ListNode(4);
        head.next = new ListNode(5);
        head.next.next = nodeToDelete;
        head.next.next.next = new ListNode(9);

        solution.deleteNode(nodeToDelete);

        assertEquals(ListNode.of(4, 5, 9), head);
    }
}