package ru.teamfour.mappers.animal;

import org.mapstruct.Mapper;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dto.animal.AnimalUpdateDto;

@Mapper(componentModel = "spring")
public interface AnimalUpdateDtoMapper {


    Animal toAnimal(AnimalUpdateDto animalUpdateDto);

}
