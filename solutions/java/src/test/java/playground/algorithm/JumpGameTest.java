package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JumpGameTest {

    private JumpGame solution = new JumpGame();

    @Test
    void case_1() {
        assertThat(solution.canJump(new int[]{2, 3, 1, 1, 4})).isTrue();
    }

    @Test
    void case_2() {
        assertThat(solution.canJump(new int[]{3, 2, 1, 0, 4})).isFalse();
    }
}