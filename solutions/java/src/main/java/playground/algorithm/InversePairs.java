package playground.algorithm;

public class InversePairs {

    private int count;

    public int reversePairs(int[] nums) {
        reversePairs(nums, 0, nums.length - 1);
        return count;
    }

    private int[] reversePairs(int[] nums, int start, int end) {
        if (start > end) {
            return new int[0];
        }
        if (start == end) {
            return new int[]{nums[start]};
        }
        int mid = (start + end) / 2;
        int[] left = reversePairs(nums, start, mid);
        int[] right = reversePairs(nums, mid + 1, end);
        int[] tmp = new int[end - start + 1];
        int i = tmp.length - 1;
        int l = left.length - 1, r = right.length - 1;
        while (l >= 0 && r >= 0) {
            // 如果左子数组的高位比右子数组高位的值大，那么记录逆序对。
            // 逆序对的数量等于右子数组剩余的元素的数量。
            if (left[l] > right[r]) {
                count += r + 1;
                tmp[i] = left[l];
                l--;
            } else {
                tmp[i] = right[r];
                r--;
            }
            i--;
        }
        while (l >= 0) {
            tmp[i--] = left[l--];
        }
        while (r >= 0) {
            tmp[i--] = right[r--];
        }
        return tmp;
    }
}
