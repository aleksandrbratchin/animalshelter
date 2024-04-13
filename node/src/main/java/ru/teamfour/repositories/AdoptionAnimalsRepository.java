package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;

import java.util.List;
import java.util.UUID;

public interface AdoptionAnimalsRepository extends JpaRepository<AdoptionAnimals, UUID> {

    List<AdoptionAnimals> findAdoptionAnimalsByAdopted(boolean b);
    List<AdoptionAnimals> findAdoptionAnimalsByTypeOfAnimal(TypeAnimal type);
    void deleteAdoptionAnimalsById(UUID id);

}
