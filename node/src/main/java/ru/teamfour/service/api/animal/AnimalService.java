package ru.teamfour.service.api.animal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dto.animal.AnimalDto;
import ru.teamfour.dto.animal.AnimalUpdateDto;

import java.util.List;
import java.util.UUID;

@Service
public interface AnimalService {

    Animal findById(@NotNull UUID id);


    AnimalDto delete(@NotNull UUID id);

    List<AnimalDto> findAllAnimal();

    List<AnimalDto> findByAdopted();

    List<AnimalDto> findAllByType(TypeAnimal type);

    AnimalDto create(@Valid AnimalUpdateDto animalUpdateDto);
    AnimalDto update(@NotNull UUID id, @Valid AnimalUpdateDto animalUpdateDto);


}
