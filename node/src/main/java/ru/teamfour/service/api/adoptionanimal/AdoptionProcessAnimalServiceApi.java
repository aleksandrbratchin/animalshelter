package ru.teamfour.service.api.adoptionanimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalCreateDto;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalInfoDto;

import java.util.UUID;

public interface AdoptionProcessAnimalServiceApi {
    AdoptionProcessAnimal findById(@NotNull UUID id);

    AdoptionProcessAnimalInfoDto createAdoption(@Valid AdoptionProcessAnimalCreateDto adoptionProcessAnimalCreateDto);

    AdoptionProcessAnimalInfoDto addfourteendays(@NotNull UUID id);
    AdoptionProcessAnimalInfoDto addthirtydays(@NotNull UUID id);

    //AdoptionProcessAnimal findById(@NotNull UUID id);
}
