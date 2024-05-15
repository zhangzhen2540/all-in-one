package indi.zz.dp;

import java.util.Arrays;

public class Solution_354 {

    public static void main(String[] args) {
//        System.out.println(maxEnvelopes(new int[][]{
//            {5, 4}, {6, 4}, {6, 7}, {2, 3}
//        }));
//        System.out.println(maxEnvelopes(new int[][]{
//            {1, 1}, {1, 1}, {1, 1}, {1, 1}
//        }));
        System.out.println(maxEnvelopes(new int[][]{
            {10, 8}, {1, 12}, {6, 15}, {2, 18}
        }));
    }

    public static int maxEnvelopes(int[][] envelopes) {
        // 对信封排序按宽度、高度
        // f(i) 第i个信封为封面，可以组成套娃个数
        // f(i) 0.x.i    当 wx < wi & hx < hi 时  max(f(x)) + 1

        if (envelopes.length <= 1) {
            return envelopes.length;
        }

        Arrays.sort(envelopes, (a, b) -> {
            if (a[0] == b[0]) {
                return a[1] - b[1];
            } else {
                return a[0] - b[0];
            }
        });

        int max = 1;
        int[] dp = new int[envelopes.length];
        Arrays.fill(dp, 1);
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < i; j++) {
                if (envelopes[j][0] < envelopes[i][0] && envelopes[j][1] < envelopes[i][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }


            max = Math.max(dp[i], max);
        }
        return max;
    }
}
