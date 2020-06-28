package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayConstructionTest {

    private ArrayConstruction solution = new ArrayConstruction();

    @Test
    void case_1() {
        int[] array = solution.constructArr(new int[]{1, 2, 3, 4, 5});

        assertThat(array).containsExactly(120, 60, 40, 30, 24);
    }
}