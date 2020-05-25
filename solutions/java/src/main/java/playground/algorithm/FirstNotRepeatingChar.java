package playground.algorithm;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FirstNotRepeatingChar {

    public char firstUniqChar(String s) {
        Map<Integer, Long> count = s.chars().boxed().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (char ch : s.toCharArray()) {
            if (count.get((int) ch) == 1) {
                return ch;
            }
        }
        return ' ';
    }
}
