package playground.algorithm;

public class MedianOfTwoSortedArrays {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length = nums1.length + nums2.length;
        if (length % 2 == 1) {
            return findMedianSortedArrays(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, length / 2);
        }
        return (findMedianSortedArrays(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, length / 2 - 1)
                + findMedianSortedArrays(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, length / 2))
                / 2;
    }

    private double findMedianSortedArrays(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
        int length1 = end1 - start1 + 1;
        int length2 = end2 - start2 + 1;
        if (length1 > length2) {
            return findMedianSortedArrays(nums2, start2, end2, nums1, start1, end1, k);
        }
        if (length1 == 0) {
            return nums2[k];
        }
        if (k == 0) {
            return Math.min(nums1[start1], nums2[start2]);
        }
        int p1 = Math.min(start1 + k / 2, nums1.length - 1);
        int p2 = start2 + (k - (p1 - start1) - 1);
        if (nums1[p1] < nums2[p2]) {
            return findMedianSortedArrays(nums1, p1 + 1, end1, nums2, start2, end2, k - (p1 - start1 + 1));
        }
        if (nums1[p1] > nums2[p2]) {
            return findMedianSortedArrays(nums1, start1, end1, nums2, p2 + 1, end2, k - (p2 - start2 + 1));
        }
        return nums1[p1];
    }
}
