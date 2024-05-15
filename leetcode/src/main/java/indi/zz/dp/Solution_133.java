package indi.zz.dp;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution_133 {
    public static void main(String[] args) {
        System.out.println(new Solution_133().largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
        System.out.println(new Solution_133().largestRectangleArea(new int[]{2, 2}));
    }

    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();

        int max = 0;
        int i = 0;
        for (; i < heights.length; i++) {
            while (!stack.isEmpty() && heights[stack.peekLast()] >= heights[i]) {
                Integer top = stack.removeLast();
                Integer lft = stack.isEmpty() ? -1 : stack.peekLast();

                max = Math.max((i - lft - 1) * heights[top], max);
            }

            stack.addLast(i);
        }

        while (!stack.isEmpty()) {
            Integer top = stack.removeLast();
            Integer lft = stack.isEmpty() ? -1 : stack.peekLast();
            max = Math.max((i - lft - 1) * heights[top], max);
        }

        return max;
    }

}
