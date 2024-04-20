package ru.teamfour.service.api.animal;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.animal.Animal;

import java.util.UUID;

@Service
public interface AnimalService {

    Animal findById(@NotNull UUID id);

/*    Animal create(Animal animal);

    Animal put(Animal animal);

    void delete(@NotNull UUID id);

    List<Animal> findAll();

    List<Animal> findByAdopted(AdoptionAnimalState adoptionAnimalState);

    List<Animal> findAllByType(TypeAnimal type);

    Animal create(@Valid AnimalDto animalAddDto);
    Animal update(@Valid AnimalDto animalAddDto);*/


}
