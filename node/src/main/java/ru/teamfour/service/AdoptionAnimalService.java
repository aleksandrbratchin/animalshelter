package ru.teamfour.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.repositories.AdoptionAnimalRepository;
import ru.teamfour.exception.BadRequestException;
import ru.teamfour.service.api.AdoptionAnimalServiceApi;

import java.util.UUID;

@Service
@Transactional
@Validated
public class AdoptionAnimalService implements AdoptionAnimalServiceApi {

    private final AdoptionAnimalRepository adoptionAnimalRepository;

    public AdoptionAnimalService(AdoptionAnimalRepository adoptionAnimalRepository) {
        this.adoptionAnimalRepository = adoptionAnimalRepository;
    }

    @Override
    public AdoptionProcessAnimal findById(@NotNull UUID id) {
        return adoptionAnimalRepository.findById(id).orElseThrow(() -> new BadRequestException("Отсутствует Id"));
    }

}
