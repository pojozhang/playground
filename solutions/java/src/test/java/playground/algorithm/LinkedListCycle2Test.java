package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LinkedListCycle2Test {

    private LinkedListCycle2 solution = new LinkedListCycle2();

    @Test
    void case_1() {
        ListNode head = new ListNode(0);
        head.next = new ListNode(1);

        assertNull(solution.detectCycle(head));
    }

    @Test
    void case_2() {
        ListNode head = new ListNode(0);
        head.next = new ListNode(1);
        head.next.next = head;

        assertEquals(head, solution.detectCycle(head));
    }
}