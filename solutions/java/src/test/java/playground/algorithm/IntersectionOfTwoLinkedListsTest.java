package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.common.ListNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntersectionOfTwoLinkedListsTest {

    private IntersectionOfTwoLinkedLists solution = new IntersectionOfTwoLinkedLists();

    @Test
    void case_1() {
        ListNode headA = new ListNode(10);
        ListNode intersection = new ListNode(20);
        headA.next = intersection;
        headA.next.next = new ListNode(13);

        ListNode headB = new ListNode(20);
        headB.next = intersection;

        assertEquals(intersection, solution.getIntersectionNode(headA, headB));
    }
}