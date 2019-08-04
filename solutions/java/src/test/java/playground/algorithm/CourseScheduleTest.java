package playground.algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseScheduleTest {

    private CourseSchedule solution = new CourseSchedule();

    @Test
    void case_1() {
        assertTrue(solution.canFinish(2, new int[][]{{1, 0}}));
    }

    @Test
    void case_2() {
        assertFalse(solution.canFinish(2, new int[][]{{1, 0}, {0, 1}}));
    }

    @Test
    void case_3() {
        assertTrue(solution.canFinish(3, new int[][]{{2, 0}, {2, 1}}));
    }
}