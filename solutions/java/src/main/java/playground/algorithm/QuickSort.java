package playground.algorithm;

import playground.util.*;

public class QuickSort {

    public void sort(int[] nums) {
        sortRecursive(nums, 0, nums.length - 1);
    }

    private void sortRecursive(int[] nums, int start, int end) {
        if (start >= end) {
            return;
        }

        int left = start, right = end;
        int key = nums[start];
        while (left < right) {
            while (left < right && nums[right] >= key) {
                right--;
            }
            while (left < right && nums[left] <= key) {
                left++;
            }

            Utils.Swap(nums, left, right);
        }
        Utils.Swap(nums, start, left);
        sortRecursive(nums, start, left - 1);
        sortRecursive(nums, left + 1, end);
    }
}