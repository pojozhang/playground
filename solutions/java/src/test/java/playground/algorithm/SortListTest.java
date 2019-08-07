package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.*;

class SortListTest {

    private SortList solution = new SortList();

    @Test
    void case_1() {
        assertEquals(ListNode.of(1, 2, 3, 4), solution.sortList(ListNode.of(4, 2, 1, 3)));
    }

    @Test
    void case_2() {
        assertEquals(ListNode.of(-1, 0, 3, 4, 5), solution.sortList(ListNode.of(-1, 5, 3, 4, 0)));
    }
}