package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MedianFinderTest {

    @Test
    void case_1() {
        MedianFinder solution = new MedianFinder();
        solution.addNum(1);
        solution.addNum(2);
        assertThat(solution.findMedian()).isEqualTo(1.5);
        solution.addNum(3);
        assertThat(solution.findMedian()).isEqualTo(2);
    }
}