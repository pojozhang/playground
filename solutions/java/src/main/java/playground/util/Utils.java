package playground.util;

import java.util.List;

public class Utils {

    public static void swap(int[] array, int first, int second) {
        int temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    @SuppressWarnings("unchecked")
    public static void swap(List list, int first, int second) {
        Object temp = list.get(first);
        list.set(first, list.get(second));
        list.set(second, temp);
    }
}