package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MergeTwoSortedListsTest {

    @Test
    void case_1() {
        ListNode list1 = new ListNode(1);
        list1.next = new ListNode(2);
        list1.next.next = new ListNode(4);

        ListNode list2 = new ListNode(1);
        list2.next = new ListNode(3);
        list2.next.next = new ListNode(4);

        ListNode mergedList = new MergeTwoSortedLists().mergeTwoLists(list1, list2);

        assertEquals(1, mergedList.val);
        assertEquals(1, mergedList.next.val);
        assertEquals(2, mergedList.next.next.val);
        assertEquals(3, mergedList.next.next.next.val);
        assertEquals(4, mergedList.next.next.next.next.val);
        assertEquals(4, mergedList.next.next.next.next.next.val);
        assertNull(mergedList.next.next.next.next.next.next);
    }
}