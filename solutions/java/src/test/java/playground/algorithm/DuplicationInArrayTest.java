package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DuplicationInArrayTest {

    private DuplicationInArray solution = new DuplicationInArray();

    @Test
    void case_1() {
        int repeatNumber = solution.findRepeatNumber(new int[]{2, 3, 1, 0, 2, 5, 3});

        assertThat(repeatNumber).isIn(2, 3);
    }
}