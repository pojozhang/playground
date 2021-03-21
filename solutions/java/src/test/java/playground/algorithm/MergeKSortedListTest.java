package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import java.lang.management.LockInfo;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class MergeKSortedListTest {

    private MergeKSortedList solution = new MergeKSortedList();

    @Test
    void case_1() {
        ListNode sortedList = solution.mergeKLists(new ListNode[]{ListNode.of(1, 4, 5), ListNode.of(1, 3, 4), ListNode.of(2, 6)});

        assertThat(sortedList).isEqualTo(ListNode.of(1, 1, 2, 3, 4, 4, 5, 6));
    }

    @Test
    void case_2() {
        ListNode sortedList = solution.mergeKLists(new ListNode[]{});

        assertThat(sortedList).isNull();
    }

    @Test
    void case_3() {
        ListNode sortedList = solution.mergeKLists(new ListNode[]{null});

        assertThat(sortedList).isNull();
    }
}