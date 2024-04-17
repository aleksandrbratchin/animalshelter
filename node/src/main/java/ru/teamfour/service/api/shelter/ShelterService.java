package ru.teamfour.service.api.shelter;


import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.shelter.Shelter;

import java.util.List;
import java.util.UUID;

public interface ShelterService {
    Shelter add(Shelter shelter);

    Shelter add(String name,
                TypeAnimal typeAnimal,
                String aboutShelter,
                String address,
                String safetyMeasures,
                String securityData,
                String workSchedule);

    void remove(UUID id);

    void removeByName(String name);

    Shelter find(UUID id);

    Shelter findByName(String name);

    List<Shelter> findAll();

    Shelter change(UUID id, Shelter shelter);



    Shelter changeAboutShelter(String name,
                               String aboutShelter);

    Shelter changeSecurity(String name,
                           String security);

    Shelter changeSafetyMeasures(String name,
                                 String safety);

    Shelter changeAddress(String name,
                          String address);

    Shelter changeTypeAnimal(String name,
                             TypeAnimal typeAnimal);

    Shelter changeWorkSchedule(String name,
                               String work);


    List<Animal> findAllAnimalsNotAdoption(UUID id);

    List<Shelter> findByTypeAnimal(TypeAnimal typeAnimal);

}
