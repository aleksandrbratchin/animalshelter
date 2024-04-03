package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeOfAnimal;

import java.util.List;
import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {
    List<Animal> findAnimalByAdopted(boolean b);
    List<Animal> findAnimalByTypeOfAnimal(TypeOfAnimal type);
    void deleteAnimalById(UUID id);
}
