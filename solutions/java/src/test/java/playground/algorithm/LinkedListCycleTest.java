package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinkedListCycleTest {

    private LinkedListCycle solution = new LinkedListCycle();

    @Test
    void case_1() {
        ListNode head = new ListNode(0);
        head.next = new ListNode(1);

        assertFalse(solution.hasCycle(head));
    }

    @Test
    void case_2() {
        ListNode head = new ListNode(0);
        head.next = new ListNode(1);
        head.next.next = head;

        assertTrue(solution.hasCycle(head));
    }
}