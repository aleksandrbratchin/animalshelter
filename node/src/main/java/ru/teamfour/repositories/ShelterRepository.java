package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.shelter.Shelter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShelterRepository extends JpaRepository<Shelter, UUID> {

    Optional<Shelter> findByName(String name);
    List<Shelter> findByTypeOfAnimal(TypeAnimal typeAnimal);

}
