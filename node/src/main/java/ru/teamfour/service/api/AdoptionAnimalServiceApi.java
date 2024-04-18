package ru.teamfour.service.api;

import jakarta.validation.constraints.NotNull;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;

import java.util.UUID;

public interface AdoptionAnimalServiceApi {


    AdoptionProcessAnimal findById(@NotNull UUID id);
}
