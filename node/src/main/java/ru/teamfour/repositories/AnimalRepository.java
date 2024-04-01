package ru.teamfour.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamfour.dao.entity.animal.Animal;

import java.util.List;
import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {
    List<Animal> findAnimalByAdopted(boolean b);
    List<Animal> findAnimalByTypeOfAnimalIgnoreCase(String type);
    void deleteAnimalById(UUID id);
}
