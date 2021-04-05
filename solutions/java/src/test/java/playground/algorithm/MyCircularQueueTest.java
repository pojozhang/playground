package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MyCircularQueueTest {

    @Test
    void case_1() {
        MyCircularQueue solution = new MyCircularQueue(3);
        solution.enQueue(1);
        solution.enQueue(2);
        solution.enQueue(3);
        solution.enQueue(4);
        assertThat(solution.Rear()).isEqualTo(3);
        assertThat(solution.isFull()).isTrue();
        assertThat(solution.deQueue()).isTrue();
        assertThat(solution.enQueue(4)).isTrue();
        assertThat(solution.Rear()).isEqualTo(4);
    }
}