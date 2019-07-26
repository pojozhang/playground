package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.*;

class ReverseNodesInKGroupTest {

    private ReverseNodesInKGroup solution = new ReverseNodesInKGroup();

    @Test
    void case_1() {
        ListNode head = solution.reverseKGroup(ListNode.of(1, 2, 3, 4, 5), 2);

        assertEquals(ListNode.of(2, 1, 4, 3, 5), head);
    }

    @Test
    void case_2() {
        ListNode head = solution.reverseKGroup(ListNode.of(1, 2, 3, 4, 5), 3);

        assertEquals(ListNode.of(3, 2, 1, 4, 5), head);
    }
}