package playground.algorithm;

import java.util.*;

public class MaxPoints {

    public int maxPoints(Point[] points) {

        if (points == null) return 0;
        if (points.length <= 2) return points.length;

        Map<String, List<Point>> cache = new HashMap<>();
        List<Point> max = null;

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

            for (String key : map.keySet()) {
                if (cache.containsKey(key)) {
                    if (map.get(key).size() > cache.get(key).size()) {
                        cache.put(key, map.get(key));
                    }
                } else {
                    cache.put(key, map.get(key));
                }
                if (max == null || map.get(key).size() > max.size()) {
                    max = map.get(key);
                }
            }

        }


//        List<Point> max = null;
//        for (List<Point> temp : cache.values()) {
//            if (max == null || temp.size() > max.size()) max = temp;
//        }

        return max == null ? 0 : max.size();
    }

    /**
     * 两个不相同的点，求所在直线位置
     * y = Kx + B 适用于不垂直于x轴的直线
     *
     * @return "y = {K} x + b"
     */
    public static String buildLine(Point a, Point b) {

        if (a.x == b.x) {
            return "x = " + a.x;
        }

        if (a.y == b.y) {
            return "y = " + a.y;
        }

        String k = buildK(a.y - b.y, a.x - b.x);

        return "y = " + k + " x + b";
    }

    public static String buildK(int a, int b) {
        int gcd = gcd(a,b);
        if (gcd != 0) {
            a /= gcd;
            b /= gcd;
        }
        return a + "/" + b;
    }

    private boolean isSamePoint(Point a, Point b) {
        return a.x == b.x && a.y == b.y;
    }

    // Greater Common Divisor
    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
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
