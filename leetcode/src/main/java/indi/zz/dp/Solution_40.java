package indi.zz.dp;

import cn.hutool.core.util.StrUtil;
import com.dywl.base.exception.BaseException;
import java.util.List;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Solution_40 {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {

        // f(i, j) = f(i - 1, j) || f(i - 1, j - val[i]) s
        boolean[][] dp = new boolean[candidates.length + 1][target + 1];
        for (int i = 0; i < dp.length; i++) {
            dp[0][i] = true;
        }

        for (int i = 1; i < candidates.length + 1; i++) {
            for (int j = 1; j <= target; j++) {
                if (candidates[i] <= j) {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - candidates[i]];
                }
            }
        }


        return null;
    }

    private static void parse(boolean[][] cans, int i, int j, int val, List<Integer> res) {

    }

    public static void main(String[] args) {
//        String str = "^(\\+)?(d{1,15})(.\\d{2})?$\n";
//        Pattern compile = Pattern.compile(str);
//        System.out.println(compile);

        System.out.println(StrUtil.format("{}{1}xxx", 1));

        System.out.println("\\d");

        log.info("abc, {}", 123);

        try {
            throw new BaseException("ABC");
        } catch (Exception e) {
            log.info("发生甚么事了", e);

            System.out.println(">>>>>>>>>>>>>>>>");
            log.info("发生甚么事了, {} {}", e.getMessage(), "555", e);
        }
    }
}
