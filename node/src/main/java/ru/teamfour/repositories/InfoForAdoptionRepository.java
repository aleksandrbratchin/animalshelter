package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.infoforadoption.InfoForAdoption;

import java.util.Optional;
import java.util.UUID;

public interface InfoForAdoptionRepository extends JpaRepository<InfoForAdoption, UUID> {
    Optional<InfoForAdoption> findInfoForAdoptionByTypeOfAnimal(TypeAnimal typeOfAnimal);
}
