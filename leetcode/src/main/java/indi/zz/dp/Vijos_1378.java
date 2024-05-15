package indi.zz.dp;

public class Vijos_1378 {

    /**
     * 对于一个给定的n*m的矩阵，矩阵中的每个元素aij据为非负整数。游戏规则如下：
     * 1. 每次取数时须从每行各取走一个元素，共n个。m次后取完矩阵所有的元素；
     * 2. 每次取走的各个元素只能是该元素所在行的行首或行尾；
     * 3. 每次取数都有一个得分值，为每行取数的得分之和；每行取数的得分 = 被取走的元素值*2^i，其中i表示第i次取数（从1开始编号）；
     * 4. 游戏结束总得分为m次取数得分之和。
     *
     * 1 <= i <= m
     *
     * f(i) 第i次取完得到分数
     *
     * f(n, i, j) 表示第n次，对于第i行，取第j列的值
     *   f(n - 1, i, j + 1)
     *   f(n - 1,
     *
     * n, i, x, y
     *
     * n - 1, i, x - 1, y
     * n - 1, i, x, y + 1
     *
     * f(i, j)   max{f(i, j + 1) + v[j] * 2 ^ n, f(i - 1, j) + v[i] * 2 ^ n}
     *
     *
     */
//    public static int solution(int[][] matrix) {
//
//    }
//    public static int solution2(int[][] matrix) {
//
//    }

}
