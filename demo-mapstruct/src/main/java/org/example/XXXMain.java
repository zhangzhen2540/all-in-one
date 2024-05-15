package org.example;

import com.alibaba.fastjson.JSON;

import java.util.Date;

public class XXXMain {

    public static void main(String[] args) {
        Person person = new Person();
        person.setName("ABC");

        person.setBirthday(new Date());
        person.setId(1L);
        System.out.println(JSON.toJSONString(CarMapper.INSTANCE.personCon(person)));

        Car car = new Car();
        car.setId(1L);
        car.setPerson(person);
        System.out.println(JSON.toJSONString(car));
        System.out.println(JSON.toJSONString(CarMapper.INSTANCE.carToCarDTO(car)));


    }
}