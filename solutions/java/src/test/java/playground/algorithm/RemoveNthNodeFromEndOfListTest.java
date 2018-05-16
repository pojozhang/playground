package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveNthNodeFromEndOfListTest {

    private RemoveNthNodeFromEndOfList solution = new RemoveNthNodeFromEndOfList();

    @Test
    void case_1() {
        RemoveNthNodeFromEndOfList.ListNode head = new RemoveNthNodeFromEndOfList.ListNode(1);
        head.next = new RemoveNthNodeFromEndOfList.ListNode(2);
        head.next.next = new RemoveNthNodeFromEndOfList.ListNode(3);
        head.next.next.next = new RemoveNthNodeFromEndOfList.ListNode(4);
        head.next.next.next.next = new RemoveNthNodeFromEndOfList.ListNode(5);

        assertEquals(5, solution.removeNthFromEnd(head, 2).next.next.next.val);
    }

    @Test
    void case_2() {
        RemoveNthNodeFromEndOfList.ListNode head = new RemoveNthNodeFromEndOfList.ListNode(1);

        assertNull(solution.removeNthFromEnd(head, 1));
    }
}