package ru.teamfour.service.api;

import ru.teamfour.dao.entity.adoptionanimal.AdoptionAnimal;

import java.util.UUID;

public interface AdoptionAnimalService {


    AdoptionAnimal findById(UUID id);
}
