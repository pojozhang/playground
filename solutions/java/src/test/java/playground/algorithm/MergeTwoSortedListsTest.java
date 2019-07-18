package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MergeTwoSortedListsTest {

    private MergeTwoSortedLists solution = new MergeTwoSortedLists();

    @Test
    void case_1() {
        ListNode list1 = ListNode.of(1, 2, 4);
        ListNode list2 = ListNode.of(1, 3, 4);

        ListNode mergedList = solution.mergeTwoLists(list1, list2);

        assertEquals(ListNode.of(1, 1, 2, 3, 4, 4), mergedList);
    }
}