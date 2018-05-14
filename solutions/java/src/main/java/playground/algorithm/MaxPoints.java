package playground.algorithm;

import java.util.*;

public class MaxPoints {

    public int maxPoints(Point[] points) {

        if (points == null) return 0;
        if (points.length <= 2) return points.length;

        System.out.println(Arrays.toString(points));

        Map<String, List<Point>> cache = new HashMap<>();

        for (int i = 0, length = points.length; i < length; i++) {

            Point a = points[i];
            int times = 0;
            Map<String, List<Point>> map = new HashMap<>();

            for (int j = i + 1; j < length; j++) {

                Point b = points[j];
                if (isSamePoint(a, b)) {
                    times++;
                    continue;
                }

                String line = buildLine(a, b);
                if (map.containsKey(line)) {
                    List<Point> temp = map.get(line);
                    temp.add(b);
                    map.put(line, temp);
                } else {
                    List<Point> temp = new ArrayList<>();
                    temp.add(a);
                    temp.add(b);
                    map.put(line, temp);
                }
            }

            if (times != 0) {

                if (map.isEmpty() && cache.isEmpty()) {
                    return times + 1;
                } else {
                    for (List<Point> pointList : map.values()) {
                        for (int time = 0; time < times; time++) {
                            pointList.add(a);
                        }
                    }
                }

            }

            System.out.println(map);

            for (String key : map.keySet()) {
                if (cache.containsKey(key)) {
                    if (map.get(key).size() > cache.get(key).size()) {
                        cache.put(key, map.get(key));
                    }
                } else {
                    cache.put(key, map.get(key));
                }
            }

        }

        System.out.println(cache);

        List<Point> max = null;
        for (List<Point> temp : cache.values()) {
            if (max == null || temp.size() > max.size()) max = temp;
        }

        return max == null ? 0 : max.size();
    }

    /**
     * 两个不相同的点，求所在直线位置
     * <p>
     * y = Ax + B
     * x = By + A
     *
     * @return "{A}-{B}"
     */
    public static String buildLine(Point a, Point b) {

        if (a.x == b.x) {
            return "x = " + a.x;
        }

        if (a.y == b.y) {
            return "y = " + a.y;
        }

        String A = subtract(a.y - b.y, a.x - b.x);

        String B = subtract(a.x * b.y - a.y * b.x, a.x - b.x);

        return "y = " + A + " x + " + B;
    }

    public static String subtract(int a, int b) {

        int i = 0;

        int tmp = Math.abs(a);
        int divisor = Math.abs(b);

        while (tmp >= divisor) {
            i++;
            tmp -= divisor;
        }

        return (a < 0 && b < 0) || (a > 0 && b > 0) ?
                i + "." + tmp : "-" + i + "." + tmp;
    }

    private boolean isSamePoint(Point a, Point b) {
        return a.x == b.x && a.y == b.y;
    }

    static class Point {

        int x;
        int y;

        Point() {
            x = 0;
            y = 0;
        }

        Point(int a, int b) {
            x = a;
            y = b;
        }

        @Override
        public String toString() {
            return "[" + x + "," + y + "]";
        }
    }

}
