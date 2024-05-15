package com.zz;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class XXXX {

    public static void main(String[] args) {

        List<Person> persons = Arrays.asList(new Person(1, "1111"),
            new Person(4, "4444"),
            new Person(2, "2222"),
            new Person(3, "3333"),
            new Person(1, "xxxxxxx")
        );

        System.out.println(JSON.toJSONString(persons));

        ArrayList<Person> after = persons.stream()
            .collect(Collectors.collectingAndThen(
                Collectors.toCollection(() ->
                    new TreeSet<Person>(Comparator.comparingInt(Person::getId).reversed())
                ),
                ArrayList::new
            ));

        System.out.println(JSON.toJSONString(after));

        System.out.println(JSON.toJSONString(new TreeSet<Person>(persons)));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Person implements Comparable {
        private int id;

        private String name;

        @Override
        public int compareTo(Object o) {
            return 0;
        }
    }
}
