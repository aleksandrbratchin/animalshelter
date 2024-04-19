package ru.teamfour.mappers.animal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dto.animal.AnimalDto;

@Mapper(componentModel = "spring")
public interface AnimalDtoMapper {

    @Mapping(target = "typeAnimal", source = "typeAnimal")
    AnimalDto toAnimalDto(Animal animal);
}
