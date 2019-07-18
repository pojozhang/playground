package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OddEvenLinkedListTest {

    private OddEvenLinkedList solution = new OddEvenLinkedList();

    @Test
    void case_1() {
        ListNode head = ListNode.of(1, 2, 3, 4, 5);

        solution.oddEvenList(head);

        assertEquals(ListNode.of(1, 3, 5, 2, 4), head);
    }

    @Test
    void case_2() {
        ListNode head = ListNode.of(2, 1, 3, 5, 6, 4, 7);

        solution.oddEvenList(head);

        assertEquals(ListNode.of(2, 3, 6, 7, 1, 5, 4), head);
    }
}