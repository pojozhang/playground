package playground.algorithm;

import playground.util.Utils;

public class HeapSort {

    public void sort(int[] nums) {
        buildMaxHeap(nums);

        for (int i = nums.length - 1; i >= 0; i--) {
            Utils.swap(nums, 0, i);
            maxHeapify(nums, 0, i);
        }
    }

    private void buildMaxHeap(int[] nums) {
        for (int i = (nums.length / 2) - 1; i >= 0; i--) {
            maxHeapify(nums, i, nums.length);
        }
    }

    private void maxHeapify(int[] nums, int index, int heapSize) {
        int max = index;
        int left = 2 * index + 1;
        int right = left + 1;

        if (left < heapSize && nums[left] > nums[max]) {
            max = left;
        }
        if (right < heapSize && nums[right] > nums[max]) {
            max = right;
        }

        if (max != index) {
            Utils.swap(nums, max, index);
            maxHeapify(nums, max, heapSize);
        }
    }
}