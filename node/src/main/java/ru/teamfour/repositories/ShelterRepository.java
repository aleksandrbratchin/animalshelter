package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.shelter.Shelter;

import java.util.UUID;

public interface ShelterRepository extends JpaRepository<Shelter, UUID> {
}
