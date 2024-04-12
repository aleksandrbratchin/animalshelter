package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionAnimal;

import java.util.UUID;

public interface AdoptionAnimalRepository extends JpaRepository<AdoptionAnimal, UUID> {

}
