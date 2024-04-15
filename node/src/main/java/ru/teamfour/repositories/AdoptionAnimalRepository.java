package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;

import java.util.List;
import java.util.UUID;

public interface AdoptionAnimalRepository extends JpaRepository<AdoptionProcessAnimal, UUID> {

    List<AdoptionProcessAnimal> findAdoptionAnimalById(AdoptionProcessAnimal adoptionProcessAnimal);


}
