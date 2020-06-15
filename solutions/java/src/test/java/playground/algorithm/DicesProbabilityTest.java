package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;

class DicesProbabilityTest {

    private DicesProbability solution = new DicesProbability();

    @Test
    void case_1() {
        double[] result = solution.twoSum(1);

        assertThat(result).containsExactly(new double[]{0.16667, 0.16667, 0.16667, 0.16667, 0.16667, 0.16667}, offset(0.00001));
    }

    @Test
    void case_2() {
        double[] result = solution.twoSum(2);

        assertThat(result).containsExactly(new double[]{0.02778, 0.05556, 0.08333, 0.11111, 0.13889, 0.16667, 0.13889, 0.11111, 0.08333, 0.05556, 0.02778}, offset(0.00001));
    }
}