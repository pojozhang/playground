package playground.algorithm;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashMap;
import java.util.Map;

public class MaxPointsOnALine {

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
    }

    public int maxPoints(Point[] points) {
        int max = 0, verticalLines, duplications, tempMax;
        Map<Map.Entry<Integer, Integer>, Integer> lines = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            lines.clear();
            duplications = 1;
            verticalLines = 0;
            tempMax = 0;
            for (int j = i + 1; j < points.length; j++) {
                // 记录与points[i]重复的点，duplications初始值是1，表示points[i]本身
                if (points[j].x == points[i].x && points[j].y == points[i].y) {
                    duplications++;
                    continue;
                }
                // 垂直于x轴的直线不存在斜率
                if (points[j].x == points[i].x && points[j].y != points[i].y) {
                    verticalLines++;
                    continue;
                }
                // 不垂直于x轴的直线，存在斜率
                // 由于斜率计算用到了除法，存在精度损失，因此我们不能直接使用斜率
                //
                int dx = points[j].x - points[i].x, dy = points[j].y - points[i].y;
                int gcd = greatestCommonDivisor(dx, dy);
                Map.Entry<Integer, Integer> entry = new SimpleImmutableEntry<>(dy / gcd, dx / gcd);
                int count = 1 + lines.getOrDefault(entry, 0);
                lines.put(entry, count);
                tempMax = Math.max(tempMax, count);
            }
            tempMax = Math.max(tempMax, verticalLines);
            max = Math.max(max, tempMax + duplications);
        }
        return max;
    }

    private int greatestCommonDivisor(int a, int b) {
        return b == 0 ? a : greatestCommonDivisor(b, a % b);
    }
}
