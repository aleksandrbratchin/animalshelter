package ru.teamfour.service.impl.animal;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.mappers.animal.AnimalMapper;
import ru.teamfour.repositories.AnimalRepository;
import ru.teamfour.service.api.animal.AnimalService;

import java.util.UUID;

@Service
@Transactional
@Validated
public class AnimalServiceImpl implements AnimalService {
    private final AnimalRepository repository;
    protected final AnimalMapper animalMapper;

    public AnimalServiceImpl(AnimalRepository repository, AnimalMapper animalMapper) {
        this.repository = repository;
        this.animalMapper = animalMapper;
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



    /*    *//**
     * метод создает сущность  {@link Animal}  и сохраняет ее в БД
     *
     * @return возвращает созданную сущность
     *//*
    @Override
    public Animal create(Animal animal) {
        return repository.save(animal);
    }


    *//**
     * метод заменяет сущность  {@link Animal},
     * которая уже есть в БД с таким же UUID
     *//*
    @Override
    public Animal put(Animal animal) {
        return repository.save(animal);
    }

    *//**
     * метод удаляет сущность {@link Animal} по UUID
     *
     * @param id тип UUID
     *//*
    @Override
    public void delete(@NotNull UUID id) {
        repository.deleteById(id);

    }

    */

    /**
     * @return возвращает список всех животных
     *//*
    @Override
    public List<Animal> findAll() {
        return repository.findAll();
    }

    *//**
     * метод для поиска животных по статусу
     *//*
    @Override
    public List<Animal> findByAdopted(AdoptionAnimalState adoptionAnimalState) {
        return repository.findByAdopted(adoptionAnimalState);
    }

    *//**
     * @param type тип животного, например: DOG
     * @return возвращает список животных по указанному типу
     *//*
    @Override
    public List<Animal> findAllByType(TypeAnimal type) {
        return repository.findAnimalByTypeAnimal(type);
    }

    @Override
    public Animal create(@Valid AnimalDto animalAddDto) {
        return repository.save(animalMapper.toAnimal(animalAddDto));
    }

    @Override
    public Animal update(@Valid AnimalDto animalAddDto) {
        return repository.save(animalMapper.toAnimal(animalAddDto));
    }*/

/*    @Override
    public Animal update(UUID id,@Valid AnimalDto animalAddDto) {
        Animal animal = repository.findById(id).get();
        UUID id = animal.getId();
        return repository.save(animalMapper.toAnimal(animalAddDto));

    }*/


}
