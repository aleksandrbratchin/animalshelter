package ru.teamfour.mappers.animal;

import org.mapstruct.Mapper;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dto.animal.AnimalDto;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    AnimalDto toAnimalDto(Animal animal);

    Animal toAnimal(AnimalDto animalDto);
}
