package indi.zz.dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Solution_1483 {
    Map<Integer, Map<Integer, Integer>> pp = new HashMap<>();

    /**
     * 倍增算法，另一种动态规划
     * f(i, j) 表示节点i的第2^j个父节点
     * 由于 2^j = 2^(j - 1) + 2^(j - 1)
     * 所以 f(i, j) = f(f(i, j - 1), j - 1)
     * f(i, 0)为直接父节点
     *
     * 求k个父节点:
     *  从2的幂转换为第m位为1：
     *
     *
     */
   static class TreeAncestor {
        int[][] p;

        public TreeAncestor(int n, int[] parent) {
            p = new int[n][32];
            for (int i = 0; i < n; i++) {
                Arrays.fill(p[i], -1);

                p[i][0] = parent[i];
                p[i][31] = 1; // p[i][31] 表示当前有效节点个数
            }

            for (int j = 1; ; j++) {
                boolean over = true;
                for (int i = 0; i < n; i++) {
                    p[i][j] = p[i][j - 1] == -1 ? -1 : p[p[i][j - 1]][j - 1];
                    if (p[i][j] != -1) {
                        p[i][31]++;
                        over = false;
                    }
                }
                if (over) {
                    break;
                }
            }
        }

        public int getKthAncestor(int node, int k) {
            if (k == 0 || node == -1) {
                return node;
            }

            int ffs = ffs(k) - 1;
            int len = p[node][31];
            return ffs < len ? getKthAncestor(p[node][ffs], k - (1 << ffs)) : -1;
        }

        private static int ffs(int k) {
            if (k == 0) {
                return 0;
            }

            int pos = 1;
            while (((k >> (pos - 1)) & 1) != 1) {
                pos++;
            }
            return pos;
        }

        public static void main(String[] args) {
            System.out.println(ffs(1));
            System.out.println(ffs(2));
            System.out.println(ffs(3));
            System.out.println(ffs(4));
        }

    }

    public static void main(String[] args) {

        TreeAncestor treeAncestor = new TreeAncestor(7, new int[]{-1, 0, 0, 1, 1, 2, 2});
        System.out.println(treeAncestor.getKthAncestor(3, 1));
        System.out.println(treeAncestor.getKthAncestor(5, 2));
        System.out.println(treeAncestor.getKthAncestor(6, 3));
    }
}
