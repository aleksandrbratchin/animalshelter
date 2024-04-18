package ru.teamfour.service;

import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.repositories.AdoptionAnimalRepository;
import ru.teamfour.exception.BadRequestException;
import ru.teamfour.service.api.AdoptionAnimalServiceApi;

import java.util.UUID;

@Service
public class AdoptionAnimalService implements AdoptionAnimalServiceApi {

    private final AdoptionAnimalRepository adoptionAnimalRepository;

    public AdoptionAnimalService(AdoptionAnimalRepository adoptionAnimalRepository) {
        this.adoptionAnimalRepository = adoptionAnimalRepository;
    }

    @Override
    public AdoptionProcessAnimal findById(UUID id) {
        return adoptionAnimalRepository.findById(id).orElseThrow(() -> new BadRequestException("Отсутствует Id"));
    }

}
