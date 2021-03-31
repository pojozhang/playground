package playground.algorithm;

import java.util.*;

public class NetworkDelayTime {

    public int networkDelayTime(int[][] times, int n, int k) {
        // graph记录点和点的权重关系，key是点，value是邻接点以及对应的权重。
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
        for (int i = 0; i < times.length; i++) {
            Map<Integer, Integer> neighbors = graph.getOrDefault(times[i][0], new HashMap<>());
            graph.put(times[i][0], neighbors);
            neighbors.put(times[i][1], times[i][2]);
        }

        // distances记录起点到各个节点的最近距离。
        Map<Integer, Integer> distances = new HashMap<>();
        // 起点的邻接节点。
        Map<Integer, Integer> startNeighbors = graph.get(k);
        if (startNeighbors == null) {
            return -1;
        }
        startNeighbors.forEach(distances::put);

        Set<Integer> processedNodes = new HashSet<>();
        // 获取最近的节点。
        Integer nearestNode = findNearestNode(distances, processedNodes);
        while (nearestNode != null) {
            Map<Integer, Integer> neighbors = graph.get(nearestNode);
            if (neighbors != null) {
                // 更新该节点的邻接节点的距离。
                for (Map.Entry<Integer, Integer> e : neighbors.entrySet()) {
                    int distance = distances.get(nearestNode) + e.getValue();
                    if (!distances.containsKey(e.getKey()) || distance < distances.get(e.getKey())) {
                        distances.put(e.getKey(), distance);
                    }
                }
            }
            // 把节点标为已处理。
            processedNodes.add(nearestNode);
            nearestNode = findNearestNode(distances, processedNodes);
        }

        int maxDistances = 0;
        for (int i = 1; i <= n; i++) {
            if (i == k) {
                continue;
            }
            if (!distances.containsKey(i)) {
                return -1;
            }
            maxDistances = Math.max(maxDistances, distances.get(i));
        }
        return maxDistances;
    }

    // 获取当前为处理过的节点中距离最近的节点。
    private Integer findNearestNode(Map<Integer, Integer> distances, Set<Integer> processedNodes) {
        Optional<Map.Entry<Integer, Integer>> nearestNode = distances.entrySet().stream()
                .filter(e -> !processedNodes.contains(e.getKey()))
                .min(Comparator.comparingInt(Map.Entry::getValue));
        return nearestNode.map(Map.Entry::getKey).orElse(null);
    }
}
