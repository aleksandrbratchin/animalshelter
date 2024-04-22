package ru.teamfour.service.impl.adoptionanimal;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalCreateDto;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalInfoDto;
import ru.teamfour.mappers.adoptionanimal.AdoptionProcessAnimalInfoMapper;
import ru.teamfour.mappers.adoptionanimal.AdoptionProcessAnimalMapper;
import ru.teamfour.repositories.AdoptionProcessAnimalRepository;
import ru.teamfour.service.api.adoptionanimal.AdoptionProcessAnimalServiceApi;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessStatus.*;

@Service
@Transactional
@Validated
public class AdoptionProcessAnimalService implements AdoptionProcessAnimalServiceApi {

    private final AdoptionProcessAnimalRepository adoptionProcessAnimalRepository;
    private final AdoptionProcessAnimalMapper adoptionProcessAnimalMapper;
    private final AdoptionProcessAnimalInfoMapper adoptionProcessAnimalInfoMapper;

    public AdoptionProcessAnimalService(AdoptionProcessAnimalRepository adoptionProcessAnimalRepository, AdoptionProcessAnimalMapper adoptionProcessAnimalMapper, AdoptionProcessAnimalInfoMapper adoptionProcessAnimalInfoMapper) {
        this.adoptionProcessAnimalRepository = adoptionProcessAnimalRepository;
        this.adoptionProcessAnimalMapper = adoptionProcessAnimalMapper;
        this.adoptionProcessAnimalInfoMapper = adoptionProcessAnimalInfoMapper;
    }

    @Override
    public AdoptionProcessAnimal findById(@NotNull UUID id) {
        return adoptionProcessAnimalRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public AdoptionProcessAnimalInfoDto createAdoption(@Valid AdoptionProcessAnimalCreateDto adoptionProcessAnimalCreateDto) {
        AdoptionProcessAnimal adoptionProcessAnimal = adoptionProcessAnimalMapper.toAnimal(adoptionProcessAnimalCreateDto);
        adoptionProcessAnimal.setDate(LocalDate.now().plusDays(AdoptionProcessAnimal.AUDIT_DAYS));
        adoptionProcessAnimal.getAnimal().setAdopted(AdoptionAnimalState.PROCESS_OF_ADOPTION);
        return adoptionProcessAnimalInfoMapper.toDto(adoptionProcessAnimalRepository.save(adoptionProcessAnimal));
    }

    @Override
    public AdoptionProcessAnimalInfoDto addfourteendays(@NotNull UUID id) {
        AdoptionProcessAnimal processAnimal = findById(id);
        processAnimal.setDate(processAnimal.getDate().plusDays(14));
        return adoptionProcessAnimalInfoMapper.toDto(adoptionProcessAnimalRepository.save(processAnimal));
    }

    @Override
    public AdoptionProcessAnimalInfoDto addthirtydays(@NotNull UUID id) {
        AdoptionProcessAnimal processAnimal = findById(id);
        processAnimal.setDate(processAnimal.getDate().plusDays(30));
        return adoptionProcessAnimalInfoMapper.toDto(adoptionProcessAnimalRepository.save(processAnimal));
    }

    @Override
    public AdoptionProcessAnimalInfoDto approved(@NotNull UUID id) {
        AdoptionProcessAnimal processAnimal = findById(id);
        processAnimal.setAdoptionProcessStatus(ADOPTED);
        return adoptionProcessAnimalInfoMapper.toDto(adoptionProcessAnimalRepository.save(processAnimal));

    }

    @Override
    public AdoptionProcessAnimalInfoDto rejected(@NotNull UUID id) {
        AdoptionProcessAnimal processAnimal = findById(id);
        processAnimal.setAdoptionProcessStatus(ADOPTION_DENIED);
        return adoptionProcessAnimalInfoMapper.toDto(adoptionProcessAnimalRepository.save(processAnimal));
    }

    /**
     * получить усыновления по которым необходимо принять решение
     *
     * @return {@link List<AdoptionProcessAnimalInfoDto>}
     */
    @Override
    public List<AdoptionProcessAnimalInfoDto> receiveAdoptionsOnWhichADecisionNeedsToBeMade() {
        return adoptionProcessAnimalRepository.findByAdoptionProcessStatus(PROCESS_ADOPTION)
                .stream().filter(
                        adoptionProcessAnimal -> adoptionProcessAnimal.getDate().isEqual(LocalDate.now()) || adoptionProcessAnimal.getDate().isAfter(LocalDate.now())
                )
                .map(adoptionProcessAnimalInfoMapper::toDto)
                .toList();
    }


}
