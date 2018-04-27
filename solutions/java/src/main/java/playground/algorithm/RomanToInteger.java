package playground.algorithm;

import java.util.HashMap;
import java.util.Map;

public class RomanToInteger {

    public int romanToInt(String s) {
        final Map<Character, Integer> symbols = new HashMap<>();
        symbols.put('I', 1);
        symbols.put('V', 5);
        symbols.put('X', 10);
        symbols.put('L', 50);
        symbols.put('C', 100);
        symbols.put('D', 500);
        symbols.put('M', 1000);

        int result = 0, previous = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int value = symbols.get(s.charAt(i));
            result += value >= previous ? value : -1 * value;
            previous = value;
        }
        return result;
    }
}
