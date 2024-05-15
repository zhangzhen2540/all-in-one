package org.example.entity;

import lombok.Data;
import org.springframework.beans.ExtendedBeanInfoFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

@Data
public class User {
    private Long id;
    private String name;

}
