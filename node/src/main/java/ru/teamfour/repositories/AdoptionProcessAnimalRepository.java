package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessStatus;

import java.util.List;
import java.util.UUID;

public interface AdoptionProcessAnimalRepository extends JpaRepository<AdoptionProcessAnimal, UUID> {

    List<AdoptionProcessAnimal> findByAdoptionProcessStatus(AdoptionProcessStatus adoptionProcessStatus);

}
