package indi.zz.dp;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Solution_1510 {
    public static boolean winnerSquareGame(int n) {
// f(n, k)
// f(n, 0) = f(n - x * x, 1)    x * x <= n
        boolean[] dp = new boolean[n + 1];
        dp[0] = false;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i && !dp[i]; j++) {
                dp[i] = !dp[i - j * j];
            }
        }

        Map<Integer, Integer> p = new HashMap<>();
        for (Entry<Integer, Integer> entry : p.entrySet()) {

        }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(winnerSquareGame(1));
        System.out.println(winnerSquareGame(2));
        System.out.println(winnerSquareGame(3));
        System.out.println(winnerSquareGame(4));
        System.out.println(winnerSquareGame(7));
        // 1 4
        // f(6, 1)  f(3, 1)
        //
    }
}
