package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReverseLinkedListTest {

    private ReverseLinkedList solution = new ReverseLinkedList();

    @Test
    void case_1() {
        assertEquals(ListNode.of(5, 4, 3, 2, 1), solution.reverseList(ListNode.of(1, 2, 3, 4, 5)));
        assertEquals(ListNode.of(5, 4, 3, 2, 1), solution.reverseListRecursively(ListNode.of(1, 2, 3, 4, 5)));
    }
}