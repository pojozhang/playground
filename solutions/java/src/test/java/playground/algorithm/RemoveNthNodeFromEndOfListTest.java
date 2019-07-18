package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RemoveNthNodeFromEndOfListTest {

    private RemoveNthNodeFromEndOfList solution = new RemoveNthNodeFromEndOfList();

    @Test
    void case_1() {
        ListNode head = ListNode.of(1, 2, 3, 4, 5);

        assertEquals(5, solution.removeNthFromEnd(head, 2).next.next.next.val);
    }

    @Test
    void case_2() {
        ListNode head = ListNode.of(1);

        assertNull(solution.removeNthFromEnd(head, 1));
    }
}