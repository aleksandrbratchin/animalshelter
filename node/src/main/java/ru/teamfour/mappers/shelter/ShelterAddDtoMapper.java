package ru.teamfour.mappers.shelter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dto.shelter.ShelterAddDto;

@Mapper(componentModel = "spring")
public interface ShelterAddDtoMapper {

    @Mapping(target = "typeOfAnimal", source = "typeAnimal")
    Shelter toShelter(ShelterAddDto shelterInfoDto);

    @Mapping(target = "typeAnimal", source = "typeOfAnimal")
    ShelterAddDto toShelterDto(Shelter shelter);

}
