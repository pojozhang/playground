package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.assertj.core.api.Assertions.assertThat;

class RemoveDuplicatesFromSortedList2Test {

    private RemoveDuplicatesFromSortedList2 solution = new RemoveDuplicatesFromSortedList2();

    @Test
    void case_1() {
        ListNode listNode = solution.deleteDuplicates(ListNode.of(1, 2, 3, 3, 4, 4, 5));

        assertThat(listNode).isEqualTo(ListNode.of(1, 2, 5));
    }

    @Test
    void case_2() {
        ListNode listNode = solution.deleteDuplicates(ListNode.of(1, 1, 1, 2, 3));

        assertThat(listNode).isEqualTo(ListNode.of(2, 3));
    }
}