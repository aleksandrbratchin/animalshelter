package ru.teamfour.service.impl.animal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dto.animal.AnimalDto;
import ru.teamfour.dto.animal.AnimalUpdateDto;
import ru.teamfour.mappers.animal.AnimalMapper;
import ru.teamfour.mappers.animal.AnimalUpdateDtoMapper;
import ru.teamfour.repositories.AnimalRepository;
import ru.teamfour.service.api.animal.AnimalService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Validated
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository repository;
    private final AnimalMapper animalMapper;
    private final AnimalUpdateDtoMapper animalUpdateDtoMapper;

    public AnimalServiceImpl(AnimalRepository repository, AnimalMapper animalMapper, AnimalUpdateDtoMapper animalUpdateDtoMapper) {
        this.repository = repository;
        this.animalMapper = animalMapper;
        this.animalUpdateDtoMapper = animalUpdateDtoMapper;
    }

    /**
     * метод находит сущность {@link Animal} по UUID
     *
     * @param id тип UUID
     */
    @Override
    public Animal findById(@NotNull UUID id) {
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }



    /**
     * метод создает сущность  {@link Animal}  и сохраняет ее в БД
     *
     * @return возвращает созданную сущность
     */
    @Override
    public AnimalDto create(@Valid AnimalUpdateDto animalUpdateDto) {
        Animal animal = animalUpdateDtoMapper.toAnimal(animalUpdateDto);
        Animal animalNew = repository.save(animal);
        AnimalDto animalDto = animalMapper.toAnimalDto(animalNew);
        return animalDto;
    }
    /*

     */

    /**
     * метод заменяет сущность  {@link Animal},
     * которая уже есть в БД с таким же UUID
     *//*
     */

    /**
     * метод удаляет сущность {@link Animal} по UUID
     *
     * @param id тип UUID
     */
    @Override
    public AnimalDto delete(@NotNull UUID id) {
        Animal animal = repository.findById(id).orElseThrow(IllegalArgumentException::new);
        repository.delete(animal);
        return animalMapper.toAnimalDto(animal);

    }


    /**
     * @return возвращает список всех животных
     */

    @Override
    public List<AnimalDto> findAllAnimal() {
        return repository.findAll().stream()
                .map(animalMapper::toAnimalDto)
                .toList();
    }

    /**
     * метод для поиска животных по статусу
     */
    @Override
    public List<AnimalDto> findByAdopted() {
        return repository.findByAdopted(AdoptionAnimalState.ADOPTED).stream()
                .map(animalMapper::toAnimalDto)
                .toList();
    }

    /**
     * @param type тип животного, например: DOG
     * @return возвращает список животных по указанному типу
     */
    @Override
    public List<AnimalDto> findAllByType(TypeAnimal type) {
        return repository.findAnimalByTypeAnimal(type).stream()
                .map(animalMapper::toAnimalDto)
                .toList();
    }

    @Override// метод реализуешь или переопредеяешь. Нужна пока программируешь. Она будет ругаться, если такого метода нет в родительском классе, в котором наследуешься
    public AnimalDto update(@NotNull UUID id, @Valid AnimalUpdateDto animalUpdateDto) {
        Animal animal = repository.findById(id).orElseThrow(IllegalArgumentException::new);
        Animal animalNew = animalUpdateDtoMapper.toAnimal(animalUpdateDto);
        animal.setTypeAnimal(animalNew.getTypeAnimal());
        animal.setName(animalNew.getName());// доделать, чтобы были поля, что в AnimalUpdate
        animal.setAge(animalNew.getAge());
        animal.setBreed(animalNew.getBreed());
        animal.setHabits(animalNew.getHabits());
        animal.setAdopted(animalNew.getAdopted());

        Animal saveAnimal = repository.save(animal);
        AnimalDto animalDto = animalMapper.toAnimalDto(saveAnimal);
        return animalDto;


    }


}
