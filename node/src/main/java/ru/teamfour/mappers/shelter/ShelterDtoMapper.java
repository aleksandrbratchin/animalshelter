package ru.teamfour.mappers.shelter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dto.shelter.ShelterInfoDto;
import ru.teamfour.mappers.animal.AnimalMapper;

@Mapper(componentModel = "spring")
@Getter
@Setter
@NoArgsConstructor
public abstract class ShelterDtoMapper {
    @Autowired
    protected AnimalMapper animalMapper;

    @Mapping(target = "animals", expression = "java(shelterInfoDto.getAnimals().stream().map(animalDto -> animalMapper.toAnimal(animalDto)).toList())")
    public abstract Shelter toShelter(ShelterInfoDto shelterInfoDto);

    @Mapping(target = "animals", expression = "java(shelter.getAnimals().stream().map(animal -> animalMapper.toAnimalDto(animal)).toList())")
    public abstract ShelterInfoDto toShelterDto(Shelter shelter);

}
