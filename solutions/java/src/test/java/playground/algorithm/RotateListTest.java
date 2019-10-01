package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.*;

class RotateListTest {

    private RotateList solution = new RotateList();

    @Test
    void case_1() {
        ListNode input = ListNode.of(1, 2, 3, 4, 5);
        ListNode output = ListNode.of(4, 5, 1, 2, 3);

        assertEquals(output, solution.rotateRight(input, 2));
    }

    @Test
    void case_2() {
        ListNode input = ListNode.of(0, 1, 2);
        ListNode output = ListNode.of(2, 0, 1);

        assertEquals(output, solution.rotateRight(input, 4));
    }

    @Test
    void case_3() {
        ListNode input = ListNode.of(1, 2);
        ListNode output = ListNode.of(1, 2);

        assertEquals(output, solution.rotateRight(input, 2));
    }
}