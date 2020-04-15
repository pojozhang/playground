package playground.algorithm;

import java.util.concurrent.*;

public class MergeSort {

    public int[] sort(int[] nums) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new MergeSortTask(nums, 0, nums.length - 1));
    }

    private static class MergeSortTask extends RecursiveTask<int[]> {

        private int[] nums;
        private int start;
        private int end;

        public MergeSortTask(int[] nums, int start, int end) {
            this.nums = nums;
            this.start = start;
            this.end = end;
        }

        @Override
        protected int[] compute() {
            if (start == end) {
                return new int[]{nums[start]};
            }
            int middle = (start + end) / 2;
            MergeSortTask left = new MergeSortTask(nums, start, middle);
            MergeSortTask right = new MergeSortTask(nums, middle + 1, end);
            right.fork();
            return merge(left.compute(), right.join());
        }

        private int[] merge(int[] nums1, int[] nums2) {
            int[] result = new int[nums1.length + nums2.length];
            int i = 0, m = 0, n = 0;
            while (m < nums1.length && n < nums2.length) {
                if (nums1[m] <= nums2[n]) {
                    result[i++] = nums1[m++];
                } else {
                    result[i++] = nums2[n++];
                }
            }
            while (m < nums1.length) {
                result[i++] = nums1[m++];
            }
            while (n < nums2.length) {
                result[i++] = nums2[n++];
            }
            return result;
        }
    }
}
