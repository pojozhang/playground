package playground.util;

public class Utils {

    public static void Swap(int[] array, int src, int dest) {
        int temp = array[src];
        array[src] = array[dest];
        array[dest] = temp;
    }
}