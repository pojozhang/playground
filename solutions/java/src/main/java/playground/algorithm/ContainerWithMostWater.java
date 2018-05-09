package playground.algorithm;

public class ContainerWithMostWater {

    public int maxArea(int[] height) {
        int max = 0;
        for (int l = 0, r = height.length - 1; l < r; ) {
            max = Math.max(max, (r - l) * Math.min(height[l], height[r]));
            if (height[r] > height[l]) {
                l++;
            } else {
                r--;
            }
        }
        return max;
    }
}
