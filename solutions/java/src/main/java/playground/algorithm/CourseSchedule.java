package playground.algorithm;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class CourseSchedule {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 记录顶点的入度。
        int[] degrees = new int[numCourses];
        Arrays.fill(degrees, 0);

        // 计算顶点的入度。
        // [1,0]表示课程1依赖课程0，则课程1的入度加1。
        for (int i = 0; i < prerequisites.length; i++) {
            degrees[prerequisites[i][0]]++;
        }

        // 队列用于存放入度为0的顶点。
        Queue<Integer> queue = new LinkedList<>();
        // 把入度为0的顶点加入队列。
        for (int i = 0; i < degrees.length; i++) {
            if (degrees[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            // 从队列中取出入度为0的顶点。
            Integer course = queue.poll();
            // 遍历课程列表，找到依赖项是course的课程。
            for (int i = 0; i < prerequisites.length; i++) {
                if (prerequisites[i][1] == course) {
                    // 入度减1。
                    degrees[prerequisites[i][0]]--;
                    // 如果入度减为0，说明该课程不再依赖其它课程，也加入队列中。
                    if (degrees[prerequisites[i][0]] == 0) {
                        queue.add(prerequisites[i][0]);
                    }
                }
            }
        }

        // 统计每个顶点的入度，如果依然有顶点的入度不为0，那么说明有环。
        for (int i = 0; i < degrees.length; i++) {
            if (degrees[i] > 0) {
                return false;
            }
        }

        return true;
    }
}
