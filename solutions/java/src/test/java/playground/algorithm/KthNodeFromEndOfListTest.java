package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.*;

class KthNodeFromEndOfListTest {

    private KthNodeFromEndOfList solution = new KthNodeFromEndOfList();

    @Test
    void case_1() {
        assertEquals(4, solution.kthToLast(ListNode.of(1, 2, 3, 4, 5), 2));
    }
}