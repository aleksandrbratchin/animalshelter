package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionAnimal;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionStatus;
import ru.teamfour.dao.entity.animal.TypeAnimal;

import java.util.List;
import java.util.UUID;

public interface AdoptionAnimalRepository extends JpaRepository<AdoptionAnimal, UUID> {

    List<AdoptionAnimal> findAdoptionAnimalById(AdoptionAnimal adoptionAnimal);


}
