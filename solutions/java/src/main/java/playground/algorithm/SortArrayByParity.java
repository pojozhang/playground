package playground.algorithm;

public class SortArrayByParity {

    public int[] sortArrayByParity(int[] array) {
        int[] output = new int[array.length];
        int left = 0, right = array.length - 1;
        while (left <= right) {
            while (left < right && array[left] % 2 == 0) {
                output[left] = array[left];
                left++;
            }
            while (left < right && array[right] % 2 == 1) {
                output[right] = array[right];
                right--;
            }
            output[left] = array[right];
            output[right] = array[left];
            left++;
            right--;
        }
        return output;
    }
}
