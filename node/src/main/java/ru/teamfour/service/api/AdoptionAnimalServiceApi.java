package ru.teamfour.service.api;

import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;

import java.util.UUID;

public interface AdoptionAnimalServiceApi {


    AdoptionProcessAnimal findById(UUID id);
}
