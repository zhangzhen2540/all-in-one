package indi.zz.dp;

public class Solution_600 {

    public static void main(String[] args) {

//        System.out.println(next1(5, 32));
//        System.out.println(next1(5, 2));
//        System.out.println(next1(5, 1));
//        System.out.println(next1(5, 0));
//        System.out.println(next1(4, 32));
//        System.out.println(next1(4, 2));
//        System.out.println(next1(4, 1));
//        System.out.println(next1(4, 0));

//        System.out.println(findIntegers(3));
        System.out.println(findIntegers(4));
        System.out.println(findIntegers(5));
        System.out.println(findIntegers(20));
    }
    //      0-1111
    // dp: {2, 3, 5, 8}
    // 4: 100   5: 101
    // x = 3
    // next = 0
    // 2

    // 0
    // 1
    // 10
    // 100
    // 101
    public static int findIntegers(int n) {

        // f(i) 表示为0..i bit位组成不连续1的数字个数
        // 第i位为0时,  f(i) = f(i - 1)
        // 第i位为1时,  f(i) = f(i - 2)
        // so: f(i) = f(i - 1) + f(i - 2)
        // f(0) = 2    计数从0开始
        // f(1) = 3

        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return 2;
        }
        if (n == 2) {
            return 3;
        }
        if (n == 3) {
            return 4;
        }

        int x = next1(n, 32);
        int[] dp = new int[x];
        dp[0] = 2;
        dp[1] = 3;
        for (int i = 2; i < dp.length; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        // 对于n，计算0-n中多少个整数满足不连续1
        // n 最高位1为 x
        // 当最高位为0，存在不连续1的数量： dp[x - 1]
        // 最高位为1时，为了保证组成的数字不大于n，取次高位1的位置 y：次高位为0: dp[y - 1]
        // 次高位为1时，取次次高位y1，依次计算

        // 最高位 m，最高位为1，如果
        //  m-1位也为1， 那么 dp[m-2] 就能保证结果不含大于n的数
        //  m-1位不为1， 取下一位1的位置 k
        //  如果 k 为 -1：表示不存在下一位1，那么 res + 1 （当前也算一个结果，但是没有更多了）
        //  如果 k 为 0: 表示下一位1就是最后一位，那么 res + 2( ..0,..1 两个结果)
        //  如果 k > 0: 结果包含： k 位为0：dp[k - 1], k 位为1，依然要去掉结果大于n的


        // 111   2 3 5

        int res = dp[x - 1];
        int next = x;
        int pre = x;
        while ((next = next1(n, next)) >= -1) {
            if (next < 0) {
                res += 1;
                break;
            }

            if (next == 0) {
                res += pre - 1 == next ? 1 : 2;
                break;
            }

            if (pre - 1 == next) {
                res += dp[next - 1];
                break;
            }

            res += dp[next - 1];

            pre = next;
        }

        return res;
    }

    // po高位开始，下一位为0的位置
    private static int next1(int n, int po) {
        if (po < 0) {
            return -1;
        }
        int x = 0;
        po --;
        while (po >= 0) {
            if ((n >> po & 1) == 1) {
                break;
            }
            po--;
        }

        return po;
    }

}
