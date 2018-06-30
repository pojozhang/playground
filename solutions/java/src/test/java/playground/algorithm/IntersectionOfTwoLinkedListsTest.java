package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntersectionOfTwoLinkedListsTest {

    private IntersectionOfTwoLinkedLists solution = new IntersectionOfTwoLinkedLists();

    @Test
    void case_1() {
        IntersectionOfTwoLinkedLists.ListNode headA = new IntersectionOfTwoLinkedLists.ListNode(10);
        IntersectionOfTwoLinkedLists.ListNode intersection = new IntersectionOfTwoLinkedLists.ListNode(20);
        headA.next = intersection;
        headA.next.next = new IntersectionOfTwoLinkedLists.ListNode(13);

        IntersectionOfTwoLinkedLists.ListNode headB = new IntersectionOfTwoLinkedLists.ListNode(20);
        headB.next = intersection;

        assertEquals(intersection, solution.getIntersectionNode(headA, headB));
    }
}