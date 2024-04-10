package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.infoForAdoption.InfoForAdoption;

import java.util.Optional;
import java.util.UUID;

public interface InfoForAdoptionRepository extends JpaRepository<InfoForAdoption, UUID> {
    Optional<InfoForAdoption> findInfoForAdoptionByTypeAnimal(TypeAnimal typeAnimal);
}
