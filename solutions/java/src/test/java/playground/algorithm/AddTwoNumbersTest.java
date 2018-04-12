package playground.algorithm;

import org.junit.jupiter.api.Test;

import playground.algorithm.AddTwoNumbers.ListNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddTwoNumbersTest {

    @Test
    public void case_1() {

        AddTwoNumbers solution = new AddTwoNumbers();

        ListNode listnode = solution.buildNode(1999);

        assertEquals(9, listnode.val);
        assertEquals(9, listnode.next.val, listnode.toString());
        assertEquals(9, listnode.next.next.val);
        assertEquals(1, listnode.next.next.next.val);
    }

    @Test
    public void case_2() {

        AddTwoNumbers solution = new AddTwoNumbers();

        long result = solution.buildNum(solution.buildNode(1999));
        assertEquals(1999, result);
    }

    @Test
    public void case_3() {

        AddTwoNumbers solution = new AddTwoNumbers();

        ListNode l1 = solution.buildNode(new int[] { 0 });

        ListNode l2 = solution.buildNode(new int[] { 2, 7, 8 });

        ListNode result = solution.addTwoNumbers(l1, l2);
        assertEquals(2, result.val, result.toString());
    }

}