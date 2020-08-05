package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RobotMoveTest {

    private RobotMove solution = new RobotMove();

    @Test
    void case_1() {
        assertThat(solution.movingCount(2, 3, 1)).isEqualTo(3);
    }

    @Test
    void case_2() {
        assertThat(solution.movingCount(3, 1, 0)).isEqualTo(1);
    }
}