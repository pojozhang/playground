package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MedianOfTwoSortedArraysTest {

    private MedianOfTwoSortedArrays solution = new MedianOfTwoSortedArrays();

    @Test
    void case_1() {
        double median = solution.findMedianSortedArrays(new int[]{1, 3}, new int[]{2});

        assertThat(median).isEqualTo(2);
    }

    @Test
    void case_2() {
        double median = solution.findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4});

        assertThat(median).isEqualTo(2.5);
    }

    @Test
    void case_3() {
        double median = solution.findMedianSortedArrays(new int[]{0, 0}, new int[]{0, 0});

        assertThat(median).isZero();
    }

    @Test
    void case_4() {
        double median = solution.findMedianSortedArrays(new int[]{}, new int[]{1});

        assertThat(median).isEqualTo(1);
    }

    @Test
    void case_5() {
        double median = solution.findMedianSortedArrays(new int[]{2}, new int[]{});

        assertThat(median).isEqualTo(2);
    }

    @Test
    void case_6() {
        double median = solution.findMedianSortedArrays(new int[]{1, 3}, new int[]{2, 7});

        assertThat(median).isEqualTo(2.5);
    }
}