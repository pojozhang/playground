package playground.algorithm;

import org.junit.jupiter.api.Test;
import playground.algorithm.MaxPoints.Point;

import java.util.List;

public class MaxPointsTest {

    @Test
    public void should_find_line_by_two_point() {
        Point a = new Point(1, 2);
        Point b = new Point(2, 1);

        System.out.println(MaxPoints.buildLine(a, b));

        a = new Point(3, 4);
        b = new Point(2, 1);

        System.out.println(MaxPoints.buildLine(a, b));

        a = new Point(2, 3);
        b = new Point(1, 2);

        System.out.println(MaxPoints.buildLine(a, b));

        a = new Point(1, 4);
        b = new Point(1, 2);

        System.out.println(MaxPoints.buildLine(a, b));
    }

    @Test
    public void should_subtract_int() {
        System.out.println(MaxPoints.subtract(-9, 3));
        System.out.println(MaxPoints.subtract(-2, -3));
        System.out.println(MaxPoints.subtract(1, 4));
    }

    @Test
    public void should_find_max_point() {

        Point a = new Point(1, 2);
        Point a1 = new Point(2, 1);
        Point a2 = new Point(0, 3);
        Point a3 = new Point(3, 0);


        Point b1 = new Point(5, 0);
        Point b2 = new Point(0, 5);
        Point b3 = new Point(1, 4);
        Point b4 = new Point(4, 1);
        Point b5 = new Point(2, 3);
        Point b6 = new Point(3, 2);

        Point[] points = {a, a1, a2, a3, b1, b2, b3, b4, b5, b6};

        System.out.println(new MaxPoints().maxPoints(points));
    }

    @Test
    public void should_find_max_point_on_the_same_point() {

        Point[] points = List.of(
                new Point(1, 2),
                new Point(1, 2),
                new Point(2, 3),
                new Point(1, 2)
        ).toArray(new Point[]{});

        System.out.println(new MaxPoints().maxPoints(points));
    }

    @Test
    public void should_find_max_point_with_one_point() {

        Point a = new Point(0, 0);

        Point[] points = {a};

        System.out.println(new MaxPoints().maxPoints(points));
    }

    //[[84,250],[0,0],[1,0],[0,-70],[0,-70],[1,-1],[21,10],[42,90],[-42,-230]]

    @Test
    public void should_find_max_point_with_0_0() {

        Point[] points = List.of(
                new Point(84, 250),
                new Point(0, 0),
                new Point(1, 0),
                new Point(0, -70),
                new Point(0, -70),
                new Point(1, -1),
                new Point(21, 10),
                new Point(42, 90),
                new Point(-42, -230)
        ).toArray(new Point[]{});

        System.out.println(new MaxPoints().maxPoints(points));

    }
}
