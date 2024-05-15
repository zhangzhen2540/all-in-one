package indi.zz.dp;

import java.util.Arrays;

public class Solution_902 {
    public static int atMostNGivenDigitSet(String[] digits, int n) {
        int res = 0;
        for (int i = 0; i < digits.length; i++) {
            int v = Integer.valueOf(digits[i]);
            res = res | (1 << v);
        }

        int cnt = 0;
        for (int i = 1; i <= n; i++) {
            int mark = mark(i);
            if (mark == (mark & res)) {
                cnt++;
            }
        }

        return cnt;
    }

    public static int mark(int k) {
        int res = 0;
        while (k > 0) {
            int m = k % 10;
            k = k / 10;
            res = res | (1 << m);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(atMostNGivenDigitSet(new String[]{"1", "3", "5", "7"}, 100));
        System.out.println(atMostNGivenDigitSet(new String[]{"7"}, 8));
        System.out.println(atMostNGivenDigitSet(new String[]{"1", "2"}, 8));
        System.out.println(atMostNGivenDigitSet(new String[]{"1", "2"}, 20));
    }
}
