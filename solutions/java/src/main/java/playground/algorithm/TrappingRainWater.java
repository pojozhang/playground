package playground.algorithm;

public class TrappingRainWater {

    public int trap(int[] height) {
        int area = 0, left = 0, right = height.length - 1, leftMax = -1, rightMax = -1;
        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (leftMax <= rightMax) {
                area += leftMax - height[left++];
                continue;
            }
            area += rightMax - height[right--];
        }
        return area;
    }
}
