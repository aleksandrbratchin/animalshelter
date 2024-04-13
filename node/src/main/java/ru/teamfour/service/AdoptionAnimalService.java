package ru.teamfour.service;

import org.springframework.stereotype.Service;
import ru.teamfour.repositories.AdoptionAnimalRepository;
import ru.teamfour.repositories.UserRepository;
import ru.teamfour.service.api.AdoptionAnimal;
import ru.teamfour.exception.BadRequestException;

import java.util.UUID;

@Service
public class AdoptionAnimalService implements AdoptionAnimal {

    private final AdoptionAnimalRepository adoptionAnimalRepository;
    private final UserRepository userRepository;


    public AdoptionAnimalService(AdoptionAnimalRepository adoptionAnimalRepository, UserRepository userRepository) {
        this.adoptionAnimalRepository = adoptionAnimalRepository;
        this.userRepository = userRepository;
    }

    public ru.teamfour.dao.entity.adoptionanimal.AdoptionAnimal getAdoptionAnimal (UUID id){
        if (id == null) {
            return adoptionAnimalRepository.findById(id).orElseThrow(() -> new BadRequestException("Отсутствует Id"));
        }
        return null;
    }


}
