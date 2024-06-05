package indi.zz.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinTriangle {
    public int minimumTotal(List<List<Integer>> triangle) {
        // f[i][j] = min(f[i - 1][j], f[i - 1][j - 1]) + n[i][j]
        int r = Integer.MAX_VALUE;
        int x = triangle.size();
        int y = triangle.get(x - 1).size();
        int[][] dp = new int[x + 1][y + 1];
        for (int i = 0; i < x; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][0] = 0;
        for (int i = 1; i <= x; i++) {
            for (int j = 1; j <= y; j++) {
                if (j - 1 >= triangle.get(i - 1).size()) {
                    break;
                }
                int xxx = Math.min(dp[i - 1][j - 1], dp[i - 1][j]) + triangle.get(i - 1).get(j - 1);
                dp[i][j] = xxx;
                if (i == x) {
                    r = Math.min(r, xxx);
                }
            }
        }

        return r;
    }

    public static void main(String[] args) {

    }
}
