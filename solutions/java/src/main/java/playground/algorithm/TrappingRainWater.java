package playground.algorithm;

public class TrappingRainWater {

    public int trap(int[] height) {
        int area = 0;

        // 寻找最高点
        int peek = 0;
        for (int i = 1; i < height.length; i++) {
            if (height[i] > height[peek]) {
                peek = i;
            }
        }

        // 计算最高点左侧的面积
        for (int i = 0, bump = i; i < peek; i++) {
            if (height[i] >= height[bump]) {
                bump = i;
            } else {
                area += height[bump] - height[i];
            }
        }

        // 计算最高点右侧的面积
        for (int i = height.length - 1, bump = i; i > peek; i--) {
            if (height[i] >= height[bump]) {
                bump = i;
            } else {
                area += height[bump] - height[i];
            }
        }

        return area;
    }
}
