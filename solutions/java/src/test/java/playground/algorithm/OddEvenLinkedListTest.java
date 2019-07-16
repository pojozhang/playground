package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.*;

class OddEvenLinkedListTest {

    private OddEvenLinkedList solution = new OddEvenLinkedList();

    @Test
    void case_1() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        solution.oddEvenList(head);

        assertEquals(1, head.val);
        assertEquals(3, head.next.val);
        assertEquals(5, head.next.next.val);
        assertEquals(2, head.next.next.next.val);
        assertEquals(4, head.next.next.next.next.val);
        assertNull(head.next.next.next.next.next);
    }

    @Test
    void case_2() {
        ListNode head = new ListNode(2);
        head.next = new ListNode(1);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(5);
        head.next.next.next.next = new ListNode(6);
        head.next.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next.next = new ListNode(7);

        solution.oddEvenList(head);

        assertEquals(2, head.val);
        assertEquals(3, head.next.val);
        assertEquals(6, head.next.next.val);
        assertEquals(7, head.next.next.next.val);
        assertEquals(1, head.next.next.next.next.val);
        assertEquals(5, head.next.next.next.next.next.val);
        assertEquals(4, head.next.next.next.next.next.next.val);
        assertNull(head.next.next.next.next.next.next.next);
    }
}