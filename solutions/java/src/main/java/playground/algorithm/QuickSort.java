package playground.algorithm;

import playground.util.*;

public class QuickSort {

    public void sort(int[] nums) {
        sort(nums, 0, nums.length - 1);
    }

    private void sort(int[] nums, int start, int end) {
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

            Utils.swap(nums, left, right);
        }
        Utils.swap(nums, start, left);
        sort(nums, start, left - 1);
        sort(nums, left + 1, end);
    }
}