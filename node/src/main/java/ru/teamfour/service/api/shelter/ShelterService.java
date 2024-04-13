package ru.teamfour.service.api.shelter;

import ru.teamfour.dao.entity.shelter.Shelter;

import java.util.List;
import java.util.UUID;

public interface ShelterService {
    Shelter add(Shelter shelter);

    void remove(UUID id);

    Shelter find(UUID id);

    Shelter findByName(String name);

    List<Shelter> findAll();

    Shelter change(UUID id, Shelter shelter);
    String findAllAnimalsNotAdoption(UUID id);
}
