package ru.teamfour.service.api.animal;

import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;

import java.util.List;
import java.util.UUID;

@Service
public interface AnimalService {

    Animal create(Animal animal);

    Animal put(Animal animal);

    void delete(UUID id);

    Animal findById(UUID id);

    List<Animal> findAll();

    List<Animal> findAllByAdopted(boolean b);

    List<Animal> findAllByType(TypeAnimal type);
}
