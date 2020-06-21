package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddTwoNumbers2Test {

    private AddTwoNumbers2 solution = new AddTwoNumbers2();

    @Test
    void case_1() {
        assertThat(solution.add(1, 1)).isEqualTo(2);
    }
}