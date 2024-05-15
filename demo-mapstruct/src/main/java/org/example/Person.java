package org.example;

import lombok.Data;

import java.util.Date;

@Data
public class Person {
    private String name;

    private Long id;

    private String describe;

    private Date birthday;
}
