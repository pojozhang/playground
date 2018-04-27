package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MergeTwoSortedListsTest {

    @Test
    void case_1() {
        MergeTwoSortedLists.ListNode list1 = new MergeTwoSortedLists.ListNode(1);
        list1.next = new MergeTwoSortedLists.ListNode(2);
        list1.next.next = new MergeTwoSortedLists.ListNode(4);

        MergeTwoSortedLists.ListNode list2 = new MergeTwoSortedLists.ListNode(1);
        list2.next = new MergeTwoSortedLists.ListNode(3);
        list2.next.next = new MergeTwoSortedLists.ListNode(4);

        MergeTwoSortedLists.ListNode mergedList = new MergeTwoSortedLists().mergeTwoLists(list1, list2);

        assertEquals(1, mergedList.val);
        assertEquals(1, mergedList.next.val);
        assertEquals(2, mergedList.next.next.val);
        assertEquals(3, mergedList.next.next.next.val);
        assertEquals(4, mergedList.next.next.next.next.val);
        assertEquals(4, mergedList.next.next.next.next.next.val);
        assertNull(mergedList.next.next.next.next.next.next);
    }
}