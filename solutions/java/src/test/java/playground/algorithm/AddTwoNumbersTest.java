package playground.algorithm;

import org.junit.jupiter.api.Test;

import playground.algorithm.AddTwoNumbers.ListNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddTwoNumbersTest {
    
    @Test
    void case_1() {

        AddTwoNumbers solution = new AddTwoNumbers();

        ListNode l1 = buildNode(new int[]{0});

        ListNode l2 = buildNode(new int[]{2, 7, 8});

        ListNode result = solution.addTwoNumbers(l1, l2);
        assertEquals(2, result.val, result.toString());
        assertEquals(7, result.next.val, result.toString());
        assertEquals(8, result.next.next.val, result.toString());
    }

    private ListNode buildNode(int[] num) {
        if (num.length == 1) {
            return new ListNode(num[0]);
        }
        ListNode root = new ListNode(num[0]);
        build(root, 1, num);
        return root;
    }

    public void build(ListNode root, int i, int[] num) {
        if (i < num.length) {
            ListNode tmp = new ListNode(num[i]);
            i++;
            root.next = tmp;
            build(tmp, i, num);
        }
    }

}