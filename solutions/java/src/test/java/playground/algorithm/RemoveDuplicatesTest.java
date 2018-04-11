package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveDuplicatesTest {

    @Test
    public void case_1() {
        RemoveDuplicates solution = new RemoveDuplicates();
        int[] nums = new int[]{};

        int size = solution.removeDuplicates(nums);

        assertEquals(0, size);
    }

    @Test
    public void case_2() {
        RemoveDuplicates solution = new RemoveDuplicates();
        int[] nums = new int[]{1, 1, 2};

        int size = solution.removeDuplicates(nums);

        assertEquals(1, nums[0]);
        assertEquals(2, nums[1]);
        assertEquals(2, size);
    }
}