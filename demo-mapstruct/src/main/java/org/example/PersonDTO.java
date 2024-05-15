package org.example;

import lombok.Data;

@Data
public class PersonDTO {
    private String fullName;

    private Long id;

    private String describe;

    private String birthday;
}
