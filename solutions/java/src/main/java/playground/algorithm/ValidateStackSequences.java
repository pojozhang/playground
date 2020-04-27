package playground.algorithm;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

public class ValidateStackSequences {

    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Deque<Integer> stack = new LinkedList<>();
        int pushedIndex = 0;
        for (int poppedIndex = 0; poppedIndex < popped.length; poppedIndex++) {
            if (Objects.equals(popped[poppedIndex], stack.peek())) {
                stack.pop();
                continue;
            }
            boolean found = false;
            for (; pushedIndex < pushed.length; pushedIndex++) {
                if (pushed[pushedIndex] == popped[poppedIndex]) {
                    pushedIndex++;
                    found = true;
                    break;
                }
                stack.push(pushed[pushedIndex]);
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}
