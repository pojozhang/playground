package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.*;

class SwapNodesInPairsTest {

    private SwapNodesInPairs solution = new SwapNodesInPairs();

    @Test
    void case_1() {
        ListNode head = ListNode.of(1, 2, 3, 4);

        ListNode newHead = solution.swapPairs(head);

        assertEquals(ListNode.of(2, 1, 4, 3), newHead);
    }
}