package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NetworkDelayTimeTest {

    private NetworkDelayTime solution = new NetworkDelayTime();

    @Test
    void case_1() {
        int time = solution.networkDelayTime(new int[][]{{2, 1, 1}, {2, 3, 1}, {3, 4, 1}}, 4, 2);

        assertThat(time).isEqualTo(2);
    }

    @Test
    void case_2() {
        int time = solution.networkDelayTime(new int[][]{{1, 2, 1}}, 2, 1);

        assertThat(time).isEqualTo(1);
    }

    @Test
    void case_3() {
        int time = solution.networkDelayTime(new int[][]{{1, 2, 1}}, 2, 2);

        assertThat(time).isEqualTo(-1);
    }
}