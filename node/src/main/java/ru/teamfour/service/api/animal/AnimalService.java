package ru.teamfour.service.api.animal;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;

import java.util.List;
import java.util.UUID;

@Service
public interface AnimalService {

    Animal create(Animal animal);

    Animal put(Animal animal);

    void delete(@NotNull UUID id);

    Animal findById(@NotNull UUID id);

    List<Animal> findAll();

    List<Animal> findByAdopted(AdoptionAnimalState adoptionAnimalState);

    List<Animal> findAllByType(TypeAnimal type);


}
