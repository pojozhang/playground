package playground.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import playground.algorithm.MaxPointsOnALine.Point;

class MaxPointsOnALineTest {

    private MaxPointsOnALine solution = new MaxPointsOnALine();

    @Test
    void case_1() {
        assertEquals(3, solution.maxPoints(
            new Point[]{
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3)
            }
        ));
    }

    @Test
    void case_2() {
        assertEquals(4, solution.maxPoints(
            new Point[]{
                new Point(1, 1),
                new Point(3, 2),
                new Point(5, 3),
                new Point(4, 1),
                new Point(2, 3),
                new Point(1, 4)
            }
        ));
    }

    @Test
    void case_3() {
        assertEquals(3, solution.maxPoints(
            new Point[]{
                new Point(0, 0),
                new Point(1, 1),
                new Point(0, 0)
            }
        ));
    }

    @Test
    void case_4() {
        assertEquals(6, solution.maxPoints(
            new Point[]{
                new Point(84, 250),
                new Point(0, 0),
                new Point(1, 0),
                new Point(0, -70),
                new Point(0, -70),
                new Point(1, -1),
                new Point(21, 10),
                new Point(42, 90),
                new Point(-42, -230)
            }
        ));
    }

    @Test
    void case_5() {
        assertEquals(3, solution.maxPoints(
            new Point[]{
                new Point(2, 3),
                new Point(3, 3),
                new Point(-5, 3)
            }
        ));
    }

    /**
     * 此案例用来测试精度损失，如果直接使用float或double计算斜率，此测试案例将会失败
     */
    @Test
    void case_6() {
        assertEquals(2, solution.maxPoints(
            new Point[]{
                new Point(0, 0),
                new Point(94911151, 94911150),
                new Point(94911152, 94911151)
            }
        ));
    }
}