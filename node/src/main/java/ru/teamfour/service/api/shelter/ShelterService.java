package ru.teamfour.service.api.shelter;

import ru.teamfour.dao.entity.shelter.Shelter;

import java.util.UUID;

public interface ShelterService {
    Shelter add(Shelter shelter);

    void remove(UUID id);

    Shelter find(UUID id);

    Shelter change(UUID id, Shelter shelter);
}
