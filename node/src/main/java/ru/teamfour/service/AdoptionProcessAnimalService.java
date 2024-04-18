package ru.teamfour.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.repositories.AdoptionProcessAnimalRepository;
import ru.teamfour.service.api.AdoptionProcessAnimalServiceApi;

import java.util.UUID;

@Service
@Transactional
@Validated
public class AdoptionProcessAnimalService implements AdoptionProcessAnimalServiceApi {

    private final AdoptionProcessAnimalRepository adoptionProcessAnimalRepository;

    public AdoptionProcessAnimalService(AdoptionProcessAnimalRepository adoptionProcessAnimalRepository) {
        this.adoptionProcessAnimalRepository = adoptionProcessAnimalRepository;
    }

    @Override
    public AdoptionProcessAnimal findById(@NotNull UUID id) {
        return adoptionProcessAnimalRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

}
