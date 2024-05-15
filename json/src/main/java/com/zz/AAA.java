package com.zz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AAA {

    public static void main(String[] args) {
        Pattern locationPattern = Pattern.compile("\\d+:\\d+,\\d+");
        String s = "4:45,0";
        Matcher matcher = locationPattern.matcher(s);
        if (matcher.matches()) {
            matcher.group(0);
        }

        List<String> list = new ArrayList<>(List.of("a", "b", "c", "d"));

        List<String> sub = new ArrayList<>(List.of("a", "b"));
        sub.add(list.get(sub.size()));

        System.out.println(sub);


        List<BigDecimal> amounts = List.of();

        System.out.println(amounts.stream()
                .reduce(BigDecimal.ZERO, AAA::add));
        System.out.println(List.of(new BigDecimal("1"), new BigDecimal("2"))
                .stream()
                .reduce(BigDecimal.ZERO, AAA::add));
        System.out.println(Arrays.asList(new BigDecimal("1"), new BigDecimal("2"), null)
                .stream()
                .reduce(BigDecimal.ZERO, AAA::add));
    }

    private static BigDecimal add(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) {
            return null;
        }
        return a.add(b);
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        // f(i,j) 前i个元素和为j的可行性

        return null;
    }



}
