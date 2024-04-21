package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;

import java.util.List;
import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {
    List<Animal> findByAdopted(AdoptionAnimalState adoptionAnimalState);

    List<Animal> findAnimalByTypeAnimal(TypeAnimal type);

}
