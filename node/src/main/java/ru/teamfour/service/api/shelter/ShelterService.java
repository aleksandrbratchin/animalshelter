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
    Shelter add(Shelter shelter);

    Shelter add(String name,
                TypeAnimal typeAnimal,
                String aboutShelter,
                String address,
                String safetyMeasures,
                String securityData,
                String workSchedule);

    void remove(@NotNull UUID id);

    void removeByName(String name);

    Shelter findById(@NotNull UUID id);

    Shelter findByName(String name);

    List<Shelter> findAll();

    Shelter change(@NotNull UUID id, Shelter shelter);



    Shelter changeAboutShelter(String name,
                               String aboutShelter);

    Shelter changeSecurity(String name,
                           String security);

    Shelter changeSafetyMeasures(String name,
                                 String safety);

    Shelter changeAddress(String name,
                          String address);

    Shelter changeTypeAnimal(String name,
                             TypeAnimal typeAnimal);

    Shelter changeWorkSchedule(String name,
                               String work);


    List<Animal> findAllAnimalsNotAdoption(@NotNull UUID id);

    List<Shelter> findByTypeAnimal(TypeAnimal typeAnimal);

    Shelter create(@Valid ShelterAddDto shelterDto);

    Shelter update(@NotNull UUID id, @Valid ShelterAddDto shelterDto);

    List<ShelterInfoDto> findAllDto();

    ShelterInfoDto findByNameDto(@NotBlank String name);

}
