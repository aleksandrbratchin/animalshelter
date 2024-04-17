package ru.teamfour.mappers.shelter;

import org.mapstruct.Mapper;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dto.shelter.ShelterAddDto;

@Mapper(componentModel = "spring")
public interface ShelterAddDtoMapper {

    Shelter toShelter(ShelterAddDto shelterInfoDto);

    ShelterAddDto toShelterDto(Shelter shelter);

}
