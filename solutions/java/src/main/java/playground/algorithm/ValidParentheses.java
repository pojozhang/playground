package playground.algorithm;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ValidParentheses {

    public boolean isValid(String s) {
        Map<Character, Character> mapping = new HashMap<>();
        mapping.put(')', '(');
        mapping.put(']', '[');
        mapping.put('}', '{');

        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);

            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
                continue;
            }

            if (!stack.isEmpty() && stack.peek() == mapping.get(ch)) {
                stack.pop();
            } else {
                return false;
            }
        }

        return stack.isEmpty();
    }
}
