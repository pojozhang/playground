package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveElementTest {

    private RemoveElement solution = new RemoveElement();

    @Test
    void case_1() {
        int[] nums = new int[]{3, 2, 2, 3};

        int size = solution.removeElement(nums, 3);

        assertEquals(2, size);
        assertEquals(2, nums[0]);
        assertEquals(2, nums[1]);
    }

    @Test
    void case_2() {
        int[] nums = new int[]{0, 1, 2, 2, 3, 0, 4, 2};

        int size = solution.removeElement(nums, 2);

        assertEquals(5, size);
        assertEquals(0, nums[0]);
        assertEquals(1, nums[1]);
        assertEquals(4, nums[2]);
        assertEquals(0, nums[3]);
        assertEquals(3, nums[4]);
    }
}