package ru.teamfour.service.api.adoptionanimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalCreateDto;

import java.util.UUID;

public interface AdoptionProcessAnimalServiceApi {
    AdoptionProcessAnimal findById(@NotNull UUID id);

    AdoptionProcessAnimal createAdoption(@Valid AdoptionProcessAnimalCreateDto adoptionProcessAnimalCreateDto);

    //AdoptionProcessAnimal findById(@NotNull UUID id);
}
