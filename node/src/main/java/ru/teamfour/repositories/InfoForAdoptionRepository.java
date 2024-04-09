package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.infoForAdoption.InfoForAdoption;

import java.util.Optional;
import java.util.UUID;

public interface InfoForAdoptionRepository extends JpaRepository<InfoForAdoption, Integer> {
    Optional<InfoForAdoption> findInfoForAdoptionById(Integer id);
}
