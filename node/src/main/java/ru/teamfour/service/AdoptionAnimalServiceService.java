package ru.teamfour.service;

import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionAnimal;
import ru.teamfour.repositories.AdoptionAnimalRepository;
import ru.teamfour.exception.BadRequestException;
import ru.teamfour.service.api.AdoptionAnimalService;

import java.util.UUID;

@Service
public class AdoptionAnimalServiceService implements AdoptionAnimalService {

    private final AdoptionAnimalRepository adoptionAnimalRepository;

    public AdoptionAnimalServiceService(AdoptionAnimalRepository adoptionAnimalRepository) {
        this.adoptionAnimalRepository = adoptionAnimalRepository;
    }

    @Override
    public AdoptionAnimal findById(UUID id) {
        return adoptionAnimalRepository.findById(id).orElseThrow(() -> new BadRequestException("Отсутствует Id"));
    }

}
