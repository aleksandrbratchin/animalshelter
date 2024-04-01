package ru.teamfour.service.api.animal;

import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.animal.Animal;

import java.util.List;
import java.util.UUID;

@Service
public interface AnimalService {
    Animal create(UUID id, String type, String name, Double age, String breed,
                  String habits, boolean adopted, UUID id_shelter);

    Animal put(UUID id, String type, String name, Double age, String breed,
               String habits, boolean adopted, UUID id_shelter);

    void delete(UUID id);

    Animal findById(UUID id);

    List<Animal> findAll();

    List<Animal> findAllByAdopted(boolean b);

    List<Animal> findAllByType(String type);
}
