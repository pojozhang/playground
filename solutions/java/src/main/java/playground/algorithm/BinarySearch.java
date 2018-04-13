package playground.algorithm;

public class BinarySearch {

    public int search(int[] array, int target) {
        return search(array, target, 0, array.length - 1);
    }

    private int search(int[] array, int target, int start, int end) {
        if (start > end) {
            return -1;
        }

        int mid = (start + end) / 2;
        if (array[mid] > target) {
            return search(array, target, start, mid - 1);
        }
        if (array[mid] < target) {
            return search(array, target, mid + 1, end);
        }
        return mid;
    }
}