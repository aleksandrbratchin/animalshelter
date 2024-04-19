package ru.teamfour.mappers.shelter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dto.shelter.ShelterInfoDto;
import ru.teamfour.mappers.animal.AnimalMapper;

@Mapper(componentModel = "spring"/*, uses = {AnimalMapper.class}*/)
@Getter
@Setter
@NoArgsConstructor
public abstract class ShelterDtoMapper {

    protected AnimalMapper animalMapper;
    @Mappings({
            @Mapping(target = "animals", expression = "java(shelterInfoDto.getAnimals().stream().map(animalDto -> animalMapper.toAnimal(animalDto)).toList())"),
            @Mapping(target = "typeOfAnimal", source = "typeAnimal")
    })
    public abstract Shelter toShelter(ShelterInfoDto shelterInfoDto);

    @Mappings({
            @Mapping(target = "animals", expression = "java(shelter.getAnimals().stream().map(animal -> animalMapper.toAnimalDto(animal)).toList())"),
            @Mapping(target = "typeAnimal", source = "typeOfAnimal")
    })
    public abstract ShelterInfoDto toShelterDto(Shelter shelter);

    public void register(AnimalMapper animalMapper) {
        this.animalMapper = animalMapper;
    }
}
