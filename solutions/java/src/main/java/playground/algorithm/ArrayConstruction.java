package playground.algorithm;

public class ArrayConstruction {

    public int[] constructArr(int[] a) {
        if (a == null || a.length < 1) {
            return a;
        }
        // leftArray是A[i]左边元素的乘积。
        int[] leftArray = new int[a.length];
        // rightArray是A[i]右边元素的乘积。
        int[] rightArray = new int[a.length];
        leftArray[0] = 1;
        rightArray[rightArray.length - 1] = 1;

        for (int i = 1; i < leftArray.length; i++) {
            leftArray[i] = leftArray[i - 1] * a[i - 1];
        }

        for (int i = rightArray.length - 2; i >= 0; i--) {
            rightArray[i] = rightArray[i + 1] * a[i + 1];
        }

        int[] result = new int[a.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = leftArray[i] * rightArray[i];
        }
        return result;
    }
}
