package ru.teamfour.service.impl.adoptionanimal;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalCreateDto;
import ru.teamfour.mappers.adoptionanimal.AdoptionProcessAnimalMapper;
import ru.teamfour.repositories.AdoptionProcessAnimalRepository;
import ru.teamfour.service.api.adoptionanimal.AdoptionProcessAnimalServiceApi;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
@Validated
public class AdoptionProcessAnimalService implements AdoptionProcessAnimalServiceApi {

    private final AdoptionProcessAnimalRepository adoptionProcessAnimalRepository;
    private final AdoptionProcessAnimalMapper adoptionProcessAnimalMapper;

    public AdoptionProcessAnimalService(AdoptionProcessAnimalRepository adoptionProcessAnimalRepository, AdoptionProcessAnimalMapper adoptionProcessAnimalMapper) {
        this.adoptionProcessAnimalRepository = adoptionProcessAnimalRepository;
        this.adoptionProcessAnimalMapper = adoptionProcessAnimalMapper;
    }

    @Override
    public AdoptionProcessAnimal findById(@NotNull UUID id) {
        return adoptionProcessAnimalRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public AdoptionProcessAnimal createAdoption(@Valid AdoptionProcessAnimalCreateDto adoptionProcessAnimalCreateDto) {
        AdoptionProcessAnimal adoptionProcessAnimal = adoptionProcessAnimalMapper.toAnimal(adoptionProcessAnimalCreateDto);
        adoptionProcessAnimal.setDate(LocalDate.now().plusDays(AdoptionProcessAnimal.AUDIT_DAYS));
        return adoptionProcessAnimalRepository.save(adoptionProcessAnimal);
    }

}
