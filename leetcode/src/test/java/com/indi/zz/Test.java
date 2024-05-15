package com.indi.zz;

import java.util.Arrays;
import java.util.List;

public class Test {


    @org.junit.Test
    public void xxx() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        System.out.println(list.subList(0, list.size()));
        System.out.println(list.subList(1, list.size()));
        System.out.println(list.subList(4, list.size()));
        System.out.println(list.subList(5, list.size()));
    }

    public static void main(String[] args) {



    }
}
