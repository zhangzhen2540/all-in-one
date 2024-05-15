package org.example;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(target = "personDto", source = "person")
    CarDTO carToCarDTO(Car car);

    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "describe", source = "describe", defaultValue = "--")
    @Mapping(target = "birthday", dateFormat = "yyyy-MM-dd")
    PersonDTO personCon(Person person);

}
