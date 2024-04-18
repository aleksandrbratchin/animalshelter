package ru.teamfour.service.api.shelter;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dto.shelter.ShelterAddDto;
import ru.teamfour.dto.shelter.ShelterInfoDto;

import java.util.List;
import java.util.UUID;

public interface ShelterService {

    Shelter findById(@NotNull UUID id);

    Shelter findByName(String name);

    List<Shelter> findAll();

    List<Animal> findAllAnimalsNotAdoption(@NotNull UUID id);

    List<Shelter> findByTypeAnimal(TypeAnimal typeAnimal);

    Shelter create(@Valid ShelterAddDto shelterDto);

    Shelter update(@NotNull UUID id, @Valid ShelterAddDto shelterDto);

    List<ShelterInfoDto> findAllDto();

    ShelterInfoDto findByNameDto(@NotBlank String name);

}
