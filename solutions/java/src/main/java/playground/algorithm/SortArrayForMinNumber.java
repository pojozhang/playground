package playground.algorithm;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SortArrayForMinNumber {

    public String minNumber(int[] nums) {
        // 此题只需要对数组中每两个数字的组合进行比较。
        return Arrays.stream(nums).boxed().map(String::valueOf)
                .sorted((n1, n2) -> (n1 + n2).compareTo(n2 + n1))
                .collect(Collectors.joining());
    }
}
