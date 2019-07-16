package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RemoveNthNodeFromEndOfListTest {

    private RemoveNthNodeFromEndOfList solution = new RemoveNthNodeFromEndOfList();

    @Test
    void case_1() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        assertEquals(5, solution.removeNthFromEnd(head, 2).next.next.next.val);
    }

    @Test
    void case_2() {
        ListNode head = new ListNode(1);

        assertNull(solution.removeNthFromEnd(head, 1));
    }
}