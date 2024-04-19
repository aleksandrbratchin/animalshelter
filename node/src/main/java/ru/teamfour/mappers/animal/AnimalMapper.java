package ru.teamfour.mappers.animal;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dto.animal.AnimalDto;
import ru.teamfour.mappers.shelter.ShelterDtoMapper;
import ru.teamfour.service.api.shelter.ShelterService;

@Mapper(componentModel = "spring")
@Getter
@Setter
@NoArgsConstructor
public abstract class AnimalMapper {

    @Autowired
    protected ShelterService shelterService;

    @Autowired
    protected ShelterDtoMapper shelterDtoMapper;

    @PostConstruct
    private void init() {
        shelterDtoMapper.register(this);
    }

    @Mapping(target = "idShelter", source = "shelter.id")
    public abstract AnimalDto toAnimalDto(Animal animal);

    @Mapping(target = "shelter", expression = "java(shelterService.findById(animalDto.getIdShelter()))")
    public abstract Animal toAnimal(AnimalDto animalDto);
}
